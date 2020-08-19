package com.quanroon.ysq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 本示例演示ThreadLocal多线程使用变量问题，以及内存溢出的情况
 * 特别是那些工具栏中，保证不要使用太多的实例
 * @date 2020/8/18 11:15
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
//        threadLocalTest();
        //模拟多线程环境下，存在内存溢出的场景
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                String data = threadLocalTest.date(finalI);
                System.out.println(data);
            }).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    private String date(int seconds){
        Date date = new Date(1000 * seconds);
        //虽然这里SimpleDateFormat为非线程安全,但我们每个ThreadLocalTest实例都会创建一个SimpleDateFormat对象，保证了每一个线程直接都是独立的，这样固然没有线程安全，
        //但是对应多线程应用程序来说，这样是非常浪费资源的。

        //如果想要应用同一个SimpleDateForamt对象，被多个线程使用，这样可以采用synchronized来进行同步，这样会导致线程出现排队状态，阻塞。
        //那么这样就可以采用ThreadLocal变量。
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }


    /**
    * @Description: ThreadLocal是一种弱引用，使用完成后，请清理，避免内存泄漏
    * @Author: quanroon.yaosq
    * @Date: 2020/8/18 15:27
    * @Param: []
    * @Return: void
    */
    private static void threadLocalTest(){
        //
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        Random random = new Random();

        IntStream.range(0,5).forEach(a -> new Thread(() ->{
            threadLocal.set(a + "," + random.nextInt(100));
            System.out.println("当前线程的值：" + threadLocal.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        threadLocal.remove();//避免内存泄漏
    }
    /**
    * @Description: 使用线程池来管理线程，并保证每个线程取得SimpleDateFormat类相对独立，
     * 同时，最多产生16副本
    * @Author: quanroon.yaosq
    * @Date: 2020/8/18 15:44
    * @Param: []
    * @Return: void
    */
    private static void multiThreadVarliable(){
         ExecutorService threadPool = Executors.newFixedThreadPool(16);

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                String data = new ThreadLocalTest().dateLocal(finalI);
                System.out.println(data);
            });
        }
        threadPool.shutdown();
    }

    private String dateLocal(int seconds){
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = ThreadSafeFormater.dateFormatThreadLocal.get();
        return dateFormat.format(date);
    }
}

class ThreadSafeFormater{
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("mm:ss"));
}
