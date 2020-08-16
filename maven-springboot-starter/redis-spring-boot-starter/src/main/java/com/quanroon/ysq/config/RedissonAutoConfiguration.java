package com.quanroon.ysq.config;

import com.quanroon.ysq.entity.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/16 18:16
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Bean
    RedissonClient redissonClient(RedissonProperties redissonProperties){
        Config config = new Config();
        String prefix = "redis://";
        if(redissonProperties.isSsl())
            prefix = "rediss://";
        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress(prefix + redissonProperties.getHost() + ":" + redissonProperties.getPort());
        if(!StringUtils.isEmpty(redissonProperties.getPassword())){
            singleServerConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
