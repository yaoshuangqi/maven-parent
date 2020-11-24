package com.quanroon.ysq.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 探讨Executors四种线程池
 * @createtime 2020/8/20 23:04
 */
public class ExecutorsFourTest {

    public static void main(String[] args) {
        singleThreadTest();
    }

    public static void singleThreadTest(){
        //newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        //newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        //newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        for (int i = 0 ; i < 10 ; i++){
            final int index = i;
            cachedThreadPool.execute(() ->{
                System.out.println(index + ",current of thread id = " + Thread.currentThread().getId());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("===> close of current thread");
                    singleThreadExecutor.shutdown();
                }
            });
        }
    }
}
