package com.quanroon.ysq.thread;

import java.util.LinkedList;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content wait notify notifyAll
 * @date 2020/8/21 10:19
 */
public class SynchronizedTest {

    public static void main(String[] args) {

        final Factory factory = new Factory();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        factory.offer();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        factory.take();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }

}

class Factory {

    private LinkedList<Object> container = new LinkedList<>();

    private Integer capacity = 5;

    public synchronized void offer() throws InterruptedException {
        while (container.size() == capacity) {
            wait();
        }

        int e = (int) Math.ceil(Math.random() * 100);
        container.push(e);
        System.out.println("生产" + e);
        notify();//唤醒被wait()方法阻塞的线程，如果有多个线程，则随机唤醒一个
    }


    public synchronized void take() throws InterruptedException {
        while (container.size() == 0) {
            wait();
        }

        Object e = container.poll();
        System.out.println("消费" + e);
        notify();
    }

}
