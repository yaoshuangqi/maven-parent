package com.quanroon.ysq.web;

import jdk.nashorn.internal.objects.annotations.Where;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @conten 测试线程安全问题
 * @date 2020/8/20 8:44
 */
@RestController
public class JmeterController {
    //假设库存
    int stock = 50;
    //锁的标记
    protected Object object = new Object();

    //假设商品id
    private String produce = "43FGGR45234";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    private Redisson redisson;

    /**
    * @Description: 方法一，对于使用synchronized,因是jvm种自动加锁和释放锁的一个操作，对分布式系统不适用。
     * 继续看方法二
    * @Author: quanroon.yaosq
    * @Date: 2020/8/27 19:24
    * @Param: []
    * @Return: java.lang.String
    */
    @RequestMapping(value = "/jmeter/test",method = RequestMethod.GET)
    public String test(){
        synchronized (object){
            String  stock1 = stringRedisTemplate.opsForValue().get("stock");
            if(stock1 != null){
                //todo 订单入库
                stock = Integer.parseInt(stock1);
                stock = stock - 1;//减库存

                stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock));

                System.out.println("库存扣减成功"+stock);
            }else {
                System.out.println("库存不足");
            }
        }
        return "hello jmeter";
    }

    @RequestMapping(value = "/jmeter/test22", method = RequestMethod.GET)
    public String test22(){
        //定义轮询次数变量
        AtomicInteger count = new AtomicInteger(1);
        System.out.println("================分割线===================");
        while (true) {
            // count of three 上报超时
            if (count.getAndIncrement() == 10) {

                break;
            }
            System.out.println("===> 叠加"+count);
        }
        System.out.println("==> 总count = " + count);
        return "success test22";
    }

    int j = 0;
    @RequestMapping(value = "/jmeter/test33", method = RequestMethod.GET)
    public String test33() throws InterruptedException {
        System.out.println("================分割线===================");
        String str = "先定义个初始值";

        Thread.sleep(3000);

        //模拟去执行一些任务，比较费时的
        str += ",,走了";

        System.out.println("==> str = " + str);
        //再去执行一些任务，比如去数据库中查询一些数据
        int  i = 0;
        //int j = 0;
        for (int a = 0; a < 10; a++){
            i = a;
            j++;
        }
        System.out.println("==> a的值= " + i+"--j =" + j);
        j= 0;

        return "success test33";
    }


    /**
     * @Description: 方法二，使用setnx 方式来模拟标记锁
     * @Author: quanroon.yaosq
     * @Date: 2020/8/27 19:24
     * @Param: []
     * @Return: java.lang.String
     */
    @RequestMapping(value = "/jmeter/test2",method = RequestMethod.GET)
    public String test2(){
        //思考的角度：1.程序运行    2.开发集成   3.业务

        //死锁（加锁后，没有释放,或者redis服务宕机）,
        //可重入锁的问题，通过计数器实现
        //阻塞，非阻塞  自旋完成
        //锁失效 要保证业务完成后，在释放锁
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(produce, "ant");//setnx
        stringRedisTemplate.expire(produce, 30, TimeUnit.SECONDS);

        //直接使用Redisson
//        RLock lock1 = redisson.getLock(produce);
//        lock1.lock();
//        lock1.delete();
        if(!lock)
            return "error";
        try {
            String  stock1 = stringRedisTemplate.opsForValue().get("stock");
            if(stock1 != null){
                //todo 订单入库
                stock = Integer.parseInt(stock1);
                stock = stock - 1;//减库存

                stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock));

                System.out.println("库存扣减成功"+stock);
            }else {
                System.out.println("库存不足");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            stringRedisTemplate.delete(produce);//删除锁
        }

        return "hello jmeter";
    }
}
