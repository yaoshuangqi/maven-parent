package com.quanroon.ysq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description mq消费者启动器
 * @createtime 2020/7/5 13:14
 */
@SpringBootApplication
public class ConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        System.out.println("rocketMQ消费者服务已启动==========>");
    }
}
