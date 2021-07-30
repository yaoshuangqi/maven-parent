package com.quanroon.ysq.demo;

import com.quanroon.ysq.MultiThreadApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description springboot单元测试
 * @createtime 2020/11/15 18:12
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultiThreadApplication.class)
public class TicketTest {

    private int count = 100;//假设一个共享变量，定义为票总数

    private Lock lock = new ReentrantLock();

    @Test
    public void ticketTest() throws InterruptedException {
        TicketRunnable tr = new TicketRunnable();

        Thread thread1 = new Thread(tr, "窗口A");
        Thread thread2 = new Thread(tr, "窗口B");
        Thread thread3 = new Thread(tr, "窗口C");
        Thread thread4 = new Thread(tr, "窗口D");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        Thread.currentThread().join();//阻塞主线
    }

    //线程类模拟一个窗口售票
    public class TicketRunnable implements Runnable{

        @Override
        public void run() {
            while (count > 0){
                lock.lock();
                try {
                    if(count > 0){
                        System.out.println(Thread.currentThread().getName() + "售出第"+ count-- + "票");
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

}
