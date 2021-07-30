package com.quanroon.ysq;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
//        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        /*HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("ddd","dd" );
        //对上述数据输出一个满二叉树
        //原始数组
        int data[] = {6,3,5,9,7,8,4,2};
        //存储满二叉树的数据，设置为16
        int btree[] = new int[16];
        int level;
        for (int i=0; i<8; i++){
            for (level = 1; btree[level] != 0;){
                if(data[i] > btree[level])
                    level = level*2+1;
                else
                    level = level*2;
            }
            btree[level]=data[i];
        }
        //输出满二叉树 0 6 3 9 2 5 7 0 0 0 4 0 0 8 0 0
        Arrays.stream(btree).forEach( ii -> System.out.print(ii+" "));*/


        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Future<?> future = cachedThreadPool.submit(() -> {
            try {
                //模拟实现处理一个耗时比较长的业务
                Thread.sleep(5000);
                System.out.println("==> 我在执行一个5s的任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "success";
        });

        if(future.isDone()){
            System.out.println("==> 任务已完成，获取任务执行的结果，更新业务状态");
            Object o = null;
            try {
                o = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("==> 任务结果："+o.toString());
        }

        System.out.println("===> 表示当前主线程完成");

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("ddd","ddd" );
    }
}
