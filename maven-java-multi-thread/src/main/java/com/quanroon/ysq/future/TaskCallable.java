package com.quanroon.ysq.future;

import java.util.concurrent.Callable;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/12/5 12:34
 */
public class TaskCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        //这个是我具体任务执行的一个方法，比如：请求住建局上报的接口，比较耗时5s

        System.out.println("==> 正在执行住建局上报的任务...");
        Thread.sleep(5000);

        return "success";
    }
}
