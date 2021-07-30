package com.quanroon.ysq.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/12/5 12:34
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        Future<String> future = cachedThreadPool.submit(new TaskCallable());

        if(future.isDone()){
            System.out.println("==> 表示已完成任务，获取任务结果："+future.get());
        }

        System.out.println("==> 主线程做其他事情：gogogogo");
    }
}
