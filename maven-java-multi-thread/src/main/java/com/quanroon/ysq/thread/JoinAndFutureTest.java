package com.quanroon.ysq.thread;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content join和future测试
 * @date 2020/11/19 15:04
 */
public class JoinAndFutureTest {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        HashMap<Object, Object> hashMap = Maps.newHashMap();
        hashMap.put("key", "33");


        JoinAndFutureTest joinAndFutureTest = new JoinAndFutureTest();
        Future<Integer> integerFuture = joinAndFutureTest.caluTwo(5);
        Future<Integer> dd = joinAndFutureTest.caluTwo(6);
       while (!integerFuture.isDone()){
           System.out.println("==> 计算中...");
           Thread.sleep(2000);
       }
        System.out.println("==> 计算结果："+integerFuture.get(3, TimeUnit.SECONDS));//get是一个阻塞过程
        integerFuture.cancel(true);//cancel内部代码中最终调用的是interrupt,它只会中断休眠的线程sleep, wait, join
        System.out.println("==> 结束,是否已取消异步任务"+integerFuture.isCancelled());
        //executorService.shutdown();
        System.out.println("==> 计算结果："+dd.get(6, TimeUnit.SECONDS));
    }

    private Future<Integer> caluTwo(Integer value){
        return executorService.submit(() -> {
            System.out.println("==> 计算"+value+"的平方根");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return value*value;
        });
    }
}
