package com.quanroon.ysq.redis_reentrant_lock;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
* @Description: Redis分布式锁，可重入锁
* @Author: quanroon.yaosq
* @Date: 2020/8/19 19:12
* @Param:
* @Return:
*/
public class RedisWithReentrantLock {

	// 定义线程变量 key:存储的key,value记录每次得到线程的计数
	private ThreadLocal<Map<String, Integer>> threadLocal = new ThreadLocal<>();
	private Jedis jedis;

	public RedisWithReentrantLock(Jedis jedis) {
		this.jedis = jedis;
	}

	private boolean _lock(String key) {
		/**
		* @nxxx: 值只能是nx和xx,如果nx，则只有当key不存在时才能set,反之，则有当key存在时候才能set
		* @expx: 值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒
		*/
		return jedis.set(key, "", "nx", "ex", 30L) != null;
	}

	private void _nulock(String key) {
		jedis.del(key);

		//jie
	}

	private Map<String, Integer> currentLockers() {
		Map<String, Integer> refs = threadLocal.get();
		if (refs != null) {
			return refs;
		}
		threadLocal.set(new HashMap<>());
		return threadLocal.get();
	}

	public boolean lock(String key) {
		Map<String, Integer> refs = currentLockers();
		Integer refCnt = refs.get(key);
		//
		if (refCnt != null) {
			refs.put(key, refCnt + 1);
			return true;
		}
		//
		boolean ok = this._lock(key);
		if (!ok) {
			return false;
		}
		refs.put(key, 1);
		return true;

	}

	public boolean nulock(String key) {
		Map<String, Integer> refs = currentLockers();
		Integer refCnt = refs.get(key);
		if (refCnt == null) {
			return false;
		}
		refCnt -= 1;
		if (refCnt > 0) {
			refs.put(key, refCnt);
		}else{
			refs.remove(key);
			this._nulock(key);
		}
		return true;

	}

	public static void main(String[] args) {
		Jedis jedis = new Jedis("172.21.1.205",6380);
		//jedis.auth("mymaster");
		RedisWithReentrantLock reentrantLock = new RedisWithReentrantLock(jedis);
		// 这个hai_1，表示同一个线程去拿锁，这个锁就使用redis中的一个特性nx xx来判断的，也就是说可以做分布式锁
		// 就比如其他线程来拿锁的时候，肯定拿不到这个hai_1的锁。
		System.out.println(reentrantLock.lock("hai_1"));

//		System.out.println(reentrantLock.lock("hai_1"));
//		System.out.println(reentrantLock.lock("hai_2"));
//		System.out.println(reentrantLock.nulock("hai_1"));
//		System.out.println(reentrantLock.nulock("hai_1"));
//		System.out.println(reentrantLock.nulock("hai_2"));
	}
}
