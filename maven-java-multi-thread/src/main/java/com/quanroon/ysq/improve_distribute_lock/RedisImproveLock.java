package com.quanroon.ysq.improve_distribute_lock;

import com.quanroon.ysq.config.SpringContextHolder;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description redis终极版的分布式锁，考虑到了很多问题，包括订阅发布，心跳检测机制，多线程，并发
 * 然而，这只是单节点的redis服务器，并不适应于多多集群环境下，再多集群环境下，可以使用redLock redisson
 * 此锁不具备可重复入※，如果需要增加，可使用计数器。
 * @createtim2020/11/16 23:07
 */
@Component
public class RedisImproveLock implements Lock {

    private static String KEY ="prefix_lock";
    private static String CHANNEL_NAME = "pubSubName";

    private static ThreadLocal<String> local = new ThreadLocal<>();
    //定时任务的执行器
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    //存放每次加锁所创建的所有定时任务
    private static ConcurrentHashMap<String, Future> futures = new ConcurrentHashMap<>();

    private JedisPool jedisPool = SpringContextHolder.getBean("jedisPool");

    @Override
    public void lock() {
        Jedis jedis = jedisPool.getResource();
        while (true){
            if(tryLock())
                return;
            //创建消息订阅者
            CountDownLatch cd1 = new CountDownLatch(1);//线程之间的协助机制类，类似一个红绿灯
            Subscriber subscriber = new Subscriber(cd1);

            //jedis中的sub操作时阻塞的，为此，另起个线程来订阅
            new Thread(new Runnable() {
                @Override
                public void run() {
                    jedis.subscribe(subscriber,CHANNEL_NAME);
                }
            }).start();

            try {
                cd1.await(30, TimeUnit.SECONDS);//阻塞等待解锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cd1 = null;
            subscriber.unsubscribe();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    //非阻塞式加锁，生产随机值，并建立心跳检测
    public boolean tryLock() {
        String uuid = local.get();
        if(local.get() == null){
            uuid = UUID.randomUUID().toString();
            local.set(uuid);
        }
        //获取redis的原始连接
        Jedis jedis = jedisPool.getResource();
        try {
            //使用setnx设置请求值，并设置失效时间
            String ret = jedis.set(KEY, uuid, "NX", "EX", 30);
            //返回”ok“则加锁成功
            if("OK".equals(ret)){
                setHeatbeat(uuid);//加锁成功则启动心跳检测，进行续命操作
                return true;
            }
        } finally {
            jedis.close();
        }
        //否则，加锁失败
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        //读取lua脚本 redis2.6后特性
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //
        Jedis jedis = jedisPool.getResource();
        //操作lua脚本
        jedis.eval(script, Arrays.asList(KEY), Arrays.asList(local.get()));
        //解锁时，要将对应的心跳检测停止
        Future future = futures.get(local.get());
        future.cancel(true);
        futures.remove(local.get());
        //解锁之后，发送通知其他被阻塞的客户端
        jedis.publish(CHANNEL_NAME, "unlock");
        jedis.close();
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //心跳检测
    private void setHeatbeat(String uuid){
        //如果有心跳检测，则直接返回
        if(futures.contains(uuid))
            return;
        //启动3s一次的心跳检测
        Future future = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //获取redis连接对象
                Jedis jedis = jedisPool.getResource();
                if(jedis.get(KEY).equals(uuid)){
                    jedis.expire(uuid,30 );
                }else {
                    //如果当前任务的加锁节点不存在，说明锁已释放，将调度任务取消
                    futures.get(uuid).cancel(true);
                    futures.remove(uuid);
                }
                jedis.close();
            }
        }, 1, 20, TimeUnit.SECONDS);
        //调度启动之后，将当前节点的对象任务存到map
        futures.put(uuid,future );
    }



    ///===============================================
    public class Subscriber extends JedisPubSub{
        private CountDownLatch cdl;

        public Subscriber(CountDownLatch cdl){
            this.cdl =cdl;
        }

        //取得订阅消息后，通过cdl唤醒阻塞的线程，再次尝试加锁
        public void onMessage(String channel, String message){
            if(cdl != null)
                cdl.countDown();
        }
    }

}
