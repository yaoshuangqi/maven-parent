package com.quanroon.ysq.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2021/5/16 22:34
 */
public class ParallelTest {

    /**
     * IO密集型建议：2*CPU，因为IO密集型线程不是一直在运行，所以可以配置多一点；
     * CPU密集型建议：因为一直在使用CPU，所以要保证线程数不能太多，可以CPU数+1；
     */
    private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    /**
     * 测试方法
     *
     * @return
     */
    private int testMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 使用Java8的CompletableFuture
     */
    private void test03() {
        long start = System.currentTimeMillis();
        List<CompletableFuture<Integer>> futureList = new ArrayList<>(5);
        futureList.add(CompletableFuture.supplyAsync(this::testMethod, executor));
        futureList.add(CompletableFuture.supplyAsync(this::testMethod, executor));
        futureList.add(CompletableFuture.supplyAsync(this::testMethod, executor));
        futureList.add(CompletableFuture.supplyAsync(this::testMethod, executor));
        futureList.add(CompletableFuture.supplyAsync(this::testMethod, executor));

        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[5]));
        CompletableFuture<List<Integer>> resultList = allFuture.thenApplyAsync(value ->
                        futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()),
                executor
        );
        List<Integer> list = resultList.join();
        System.out.println("costs: ms = " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        ParallelTest parallelTest = new ParallelTest();

//        parallelTest.test03();
//        parallelTest.test03();
//        parallelTest.test03();
        parallelTest.test04();
    }

    public void test04(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> result = executorService.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 1;
        });

        try {
            System.out.println("===>>> 正在进行异步任务处理....");
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("===>>> 程序结束");
    }
}
