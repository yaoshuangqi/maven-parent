package com.quanroon.ysq.thread_lock;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 实现Lock锁中的unlock，lock
 * @createtime 2020/8/23 19:41
 */
public class MyLock implements Lock {

    //原子操作类
    AtomicReference<Thread> owner = new AtomicReference<Thread>();
    //拿不到锁的线程，需要一个等待的列表。
    public LinkedBlockingDeque<Thread> waiter = new LinkedBlockingDeque<>();
    @Override
    public void lock() {
        //进行当前线程设置
        owner.compareAndSet(null,Thread.currentThread()); //成功则返回ture。循环进行处理
        while(!owner.compareAndSet(null,Thread.currentThread())){//如果当前线程拿不到锁
            //拿不到的情况，等待，加入等待列表中
            waiter.add(Thread.currentThread());
            LockSupport.park();//让当前线程等待(一直卡在这里)
            //如果能够执行到这段的话，证明被唤醒了，所以要从等待列表中删除
            waiter.remove(Thread.currentThread());
        }
    }
    @Override
    public void unlock() {
        //当前线程与内存中的那个线程进行比较，如果是的，把内存中的线程至空
        if(owner.compareAndSet(Thread.currentThread(),null)){
            //锁被释放了，需要告知其他线程，所有线程可以去拿锁了(唤醒所有等待线程)
            Object[] objects = waiter.toArray();//等待列表转成数组
            for(Object object :objects){//遍历，拿到所有等待的线程
                Thread next = (Thread)object;
                LockSupport.unpark(next);// 把线程唤醒
            }
        }

    }
    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean tryLock() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }

}
