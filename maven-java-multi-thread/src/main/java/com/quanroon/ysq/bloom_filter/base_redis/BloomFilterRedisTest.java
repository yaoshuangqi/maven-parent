package com.quanroon.ysq.bloom_filter.base_redis;

import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 第一步是将数据库所有的数据加载到布隆过滤器。
 * 第二步当有请求来的时候先去布隆过滤器查询，如果bf说没有，
 * 第三步直接返回。如果bf说有，在往下走之前的流程
 * @date 2020/8/20 15:49
 */
@Slf4j
@Component
public class BloomFilterRedisTest {


    static RedisBloomFilter redisBloomFilter;
    @Autowired
    public void setRedisBloomFilter(RedisBloomFilter redisBloomFilter){
        this.redisBloomFilter = redisBloomFilter;
    }

    public static void redisFilterTest() {
        int expectedInsertions = 1000;
        double fpp = 0.1;
        redisBloomFilter.delete("bloom");
        RedisToBloomFilterHelper<CharSequence> bloomFilterHelper = new RedisToBloomFilterHelper<>(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
        int j = 0;
        // 添加100个元素
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            valueList.add(i + "");
        }
        long beginTime = System.currentTimeMillis();
        redisBloomFilter.addList(bloomFilterHelper, "bloom", valueList);
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("布隆过滤器添加{}个值，耗时：{}ms", 100, costMs);
        for (int i = 0; i < 1000; i++) {
            boolean result = redisBloomFilter.contains(bloomFilterHelper, "bloom", i + "");
            if (!result) {
                j++;
            }
        }
        log.info("漏掉了{}个,验证结果耗时：{}ms", j, System.currentTimeMillis() - beginTime);
    }
}
