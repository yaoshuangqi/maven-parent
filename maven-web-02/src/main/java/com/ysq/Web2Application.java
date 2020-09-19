package com.ysq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description  @EnableRedisHttpSession注解启动session共享
 * @createtime 2020/8/28 21:43
 */
@SpringBootApplication
//@EnableRedisHttpSession
public class Web2Application {

    public static void main(String[] args) {
        SpringApplication.run(Web2Application.class, args);
    }

    @EnableAsync
    @Configuration
    class TaskPoolConfig {

        @Bean("testAsyncPool")
        public Executor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(20);
            executor.setQueueCapacity(200);
            executor.setKeepAliveSeconds(60);
            executor.setThreadNamePrefix("taskExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }
    }
}
