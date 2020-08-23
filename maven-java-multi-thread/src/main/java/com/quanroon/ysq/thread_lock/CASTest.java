package com.quanroon.ysq.thread_lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author quanroong.ysq
 * @version 1.0.0 参考：https://blog.csdn.net/bjweimengshu/article/details/78949435
 * @description CAS是Compare And Swap即比较并替换
 * 三个基本操作：内存地址V，旧的预期值A，要修改的新值B
 * ABA问题，可以采用版本号来解决
 * Java语言CAS底层如何实现：利用unsafe提供了原子性操作方法
 * @createtime 2020/8/23 20:15
 */
public class CASTest {

    private static class LockObj {

        /**
         * 拥有锁的用户
         */
        String owner;

        /**
         * 锁的过期时间
         */
        long expireTime;

        LockObj(String owner, long expireTime) {
            this.owner = owner;
            this.expireTime = expireTime;
        }
    }

    private AtomicReference<LockObj> lockObjReference = new AtomicReference<>();

    /**
     * 尝试获取锁
     * @param owner 锁的标识
     * @param expireTime 锁的过期时间(单位为秒)
     * @return 获取锁成功返回true, 否则返回false
     */
    public boolean tryLock(String owner, long expireTime) {
        LockObj lockObj = new LockObj(owner, System.currentTimeMillis() +
                expireTime * 1000);
        // 获取锁成功
        if (lockObjReference.compareAndSet(null, lockObj)) {
            return true;
        }
        // 判断锁是否失效, 避免发生死锁, 如果失效再次尝试获取锁.
        LockObj oldLockObj = lockObjReference.get();
        return (oldLockObj == null || oldLockObj.expireTime < System.currentTimeMillis())
                && lockObjReference.compareAndSet(oldLockObj, lockObj);
    }

    /**
     * 释放锁
     * 如果返回值为false, 说明当前用户没有拥有该锁, 或者曾经拥有锁但是因为锁过期而被别的用户获取了
     * @param owner 拥有锁的用户
     * @return 释放锁成功返回true, 否则返回false
     */
    public boolean unLock(String owner) {
        LockObj currentLockObj = lockObjReference.get();
        // 如果锁的拥有者是owner参数指定的用户, 才能解锁
        // 不能直接用set方法, 需要原子更新, 因为有可能锁过期突然被别的用户获取了
        return currentLockObj != null && currentLockObj.owner.equals(owner)
                && lockObjReference.compareAndSet(currentLockObj, null);
    }
}
