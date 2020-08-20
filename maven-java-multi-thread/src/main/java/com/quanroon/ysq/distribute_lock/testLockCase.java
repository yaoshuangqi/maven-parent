package com.quanroon.ysq.distribute_lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
* @Description: 测试入口
 * 行业中，如果要实现分布式锁的话，最基本的要求：
 * 1.互斥性（集群环境中业务的原子性）
 * 2.锁超时（要能够保证不出现死锁）
 * 3.阻塞和非阻塞（根据具体的的业务场景）
 * 4.可重入性（也就是同一线程能够重复获取锁）
 * 5.高可用（不能出现锁失效问题）
* @Author: quanroon.yaosq
* @Date: 2020/8/19 19:25
* @Param: 
* @Return: 
*/
public class testLockCase {
	public static void main(String[] args) {

		// 定义线程池
		ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

		// 添加10个线程获取锁
		for (int i = 1; i < 5; i++) {
			MyTask myTask = new MyTask(i);
			pool.execute(myTask);
			 System.out.println("线程池中线程数目："+pool.getPoolSize()+"，队列中等待执行的任务数目："+
			 pool.getQueue().size()+"，已执行玩别的任务数目："+pool.getCompletedTaskCount());
		}

		pool.shutdown();
	}

}
