package com.quanroon.ysq.thread_lock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description Demo是模拟CopyOnWriteArrayList和ArrayList在多线程并发情况下的，会有哪些区别
 * CopyOnWriteArrayList：提供一种线程安全的，内部采用了ReentrantLock(可重入锁)+volatile数组，
 * 因为在新增/修改/删除元素时，采用新建数组（容量+1）模式，所以适合多读少写操作（为什么每次都要新建数组，这样做的好处是不影响读操作，更新过程在副本中进行）
 *
 * ArrayList：一种非线程安全的。在多线程并发情况下，会出现ConcurrentModificationException异常
 * @createtime 2020/8/15 11:31
 */
public class CopyOnWriteArrayListTest1 {

    // TODO: list是ArrayList对象时，程序会出错。
    //private static List<String> list = new ArrayList<String>();
    private static List<String> list = new CopyOnWriteArrayList<String>();
    public static void main(String[] args) {

        // 同时启动两个线程对list进行操作！
        new MyThread("ta").start();
        new MyThread("tb").start();
    }

    private static void printAll() {
        String value = null;
        Iterator iter = list.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            int i = 0;
            while (i++ < 6) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName()+"-"+i;
                list.add(val);
                // 通过“Iterator”遍历List。
                printAll();
            }
        }
    }
}
