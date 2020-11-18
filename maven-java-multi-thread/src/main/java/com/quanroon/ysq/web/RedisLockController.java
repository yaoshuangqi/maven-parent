package com.quanroon.ysq.web;

import com.quanroon.ysq.improve_distribute_lock.RedisImproveLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/11/18 18:13
 */
@RestController
public class RedisLockController {

    private int count = 100;//预定义余票100涨

    @Autowired
    private RedisImproveLock redisImproveLock;

    @RequestMapping(value = "lock", method =RequestMethod.GET)
    public Object testRedisImproveLock() throws InterruptedException {
        redisImproveLock.lock();
        Thread.sleep(100);
        if(count <= 0){
            System.out.println("==> 不好意思，无余票了。请回");
            redisImproveLock.unlock();
            return "error";
        }
        System.out.println("==> 获取到一张票，扣减余票，剩余余票"+--count+"张");
        redisImproveLock.unlock();

        return Collections.singletonMap("success", "true");
    }
}
