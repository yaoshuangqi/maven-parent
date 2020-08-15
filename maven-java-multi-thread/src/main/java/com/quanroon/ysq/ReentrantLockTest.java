package com.quanroon.ysq;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description ReentrantLock可重入锁，可以完成synchronized的所有功能，并提供可中断，公平锁等功能
 * synchronized是由JVM自动获取锁和释放锁的机制
 * @createtime 2020/8/15 13:18
 */
public class ReentrantLockTest {

    static transient ReentrantLock lock = new ReentrantLock();//默认非公平锁
    //transient ReentrantLock lock = new ReentrantLock(true);//公平锁
    //创建条件对象，并绑定到该锁，线程只有获取到该锁后，才能调用该组件的 await()方法，而调用后，当前线程将释放锁
    //这个Condition 就是类似之前使用Object 类锁一样的。
    static private Condition condition = lock.newCondition();//创建 Condition

    public static void testCondition(){
        try {
            lock.lock();

            System.out.println("wait等待");

            condition.await();

            //通过创建 Condition 对象来使线程 wait，必须先执行 lock.lock 方法获得锁
            //:2：signal 方法唤醒

            System.out.println("signal开始唤醒锁");
            condition.signal();//condition 对象的 signal 方法可以唤醒 wait 线程
            for (int i = 0; i < 5; i++) {
                System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        Runnable t1=new MyThread();
        new Thread(t1,"t1").start();
        new Thread(t1,"t2").start();
    }
}

/**
 *
 */
class MyThread implements Runnable {

    private Lock lock = new ReentrantLock();

    public void run() {
        //加入非公平锁后，可以保证顺序打印，至于哪个线程先获得锁，哪个就先打印
        lock.lock();
        try {
            for (int i = 0; i < 5; i++)
                System.out.println(Thread.currentThread().getName() + ":" + i);
        } finally {
            lock.unlock();
        }
    }
}
