package com.quanroon.ysq;

import com.quanroon.ysq.bloom_filter.base_redis.BloomFilterRedisTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/8/20 8:42
 */
@SpringBootApplication
public class MultiThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiThreadApplication.class, args);
        BloomFilterRedisTest.redisFilterTest();
    }

}
