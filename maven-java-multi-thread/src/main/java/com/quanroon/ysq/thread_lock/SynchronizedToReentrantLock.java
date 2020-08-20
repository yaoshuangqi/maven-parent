package com.quanroon.ysq.thread_lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 采用synchronized 实现ReentrantLock
 * 分析思路参考此地址：https://www.cnblogs.com/aspirant/p/8601937.html  分析的不错。从两则使用的不同点出发，然后，利用其相同点来进行构思
 *
 * @createtime 2020/8/15 14:31
 */
public class SynchronizedToReentrantLock {
    private static final long NONE=-1;
    private long owner =NONE;

    public synchronized void lock(){
        long currentThreadId=Thread.currentThread().getId();
        if(owner==currentThreadId){
            throw new IllegalStateException("lock has been acquired by current thread");
        }

        while(this.owner!=NONE){

            System.out.println(String.format("thread %s is waiting lock", currentThreadId));
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.owner=currentThreadId;
        System.out.println(String.format("lock is acquired by thread %s", currentThreadId));

    }

    public synchronized void unlock(){

        long currentThreadId=Thread.currentThread().getId();

        if(this.owner!=currentThreadId){
            throw new IllegalStateException("Only lock owner can unlock the lock");
        }

        System.out.println(String.format("thread %s is unlocking", owner));
        owner=NONE;
        notify();

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub 给定锁
        final SynchronizedToReentrantLock lock=new SynchronizedToReentrantLock();

        //产生20个线程
        ExecutorService executor= Executors.newFixedThreadPool(20, new ThreadFactory(){
            private ThreadGroup group =new ThreadGroup("test thread group");
            {
                group.setDaemon(true);
            }
            @Override
            public Thread newThread(Runnable r) {
                // TODO Auto-generated method stub
                return new Thread(group,r);
            }});


        for(int i=0;i<20;i++){
            executor.submit(new Runnable(){

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    lock.lock();
                    System.out.println(String.format("thread %s is running...", Thread.currentThread().getId()));


                    try {
                        //执行业务逻辑
                        for (int i1 = 0; i1 < 10; i1++) {
                            System.out.println("===> i1=" + i1);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    lock.unlock();
                }});
        }


    }
}
