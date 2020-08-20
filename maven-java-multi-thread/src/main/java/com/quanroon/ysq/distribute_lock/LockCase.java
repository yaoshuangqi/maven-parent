package com.quanroon.ysq.distribute_lock;

import redis.clients.jedis.Jedis;

import java.time.LocalTime;

/**
 * 这里设置key和设置过期时间需要保持原子性, （1）设置存活时间30秒,解决客户端A获得分布式的锁，客户端A挂掉，
 * 这个锁一直存在，且不会被释放，其他客户端永远获取不到锁；
 */
public class LockCase extends RedisLock {

	public LockCase(Jedis jedis, String lockKey) {
		super(jedis, lockKey);
	}

	@SuppressWarnings("unused")
	public void lock() {
		//通过自旋，进行续命
		while (true) {
			String result = jedis.set(lockKey, lockValue, LockConstant.NOT_EXIST, LockConstant.SECONDS, 10);
			if (result.equals("OK")) {
				System.out.println("线程id:" + Thread.currentThread().getId() + "加锁成功!时间:" + LocalTime.now());
				// 开启定时刷新过期时间
				isOpenExpirationRenewal = true;
				//通过异步，解决锁失效，而业务还未完成的问题
				scheduleExpirationRenewal();
				break;
			}
			if (result == null) {
				System.out.println("线程id:" + Thread.currentThread().getId() + "获取锁失败，休眠10秒!时间:" + LocalTime.now());
			}
			// 休眠10秒
			sleepBySecond(10);
		}

	}
	// 解锁操作包含三步操作：获取值、判断和删除锁。这时你有没有想到在多线程环境下的i++操作?
	// 需要保证解锁的原子性
	// public void unlock() {
	// String value = jedis.get(lockKey);
	// if (value.equals(lockValue)) {
	// jedis.del(lockKey);
	// }
	// }

	/*
	 * 利用lua脚本保证解锁的原子性
	 * 
	 */
	public void unlock() {
		System.out.println("线程id:" + Thread.currentThread().getId() + "解锁!时间:" + LocalTime.now());

		String checkAndDelScript = "if redis.call('get', KEYS[1]) == ARGV[1] then "
				+ "return redis.call('del', KEYS[1]) " + "else " + "return 0 " + "end";
		jedis.eval(checkAndDelScript, 1, lockKey, lockValue);
		isOpenExpirationRenewal = false;
	}
}
