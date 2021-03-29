package com.ysq.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2021/3/29 21:29
 */
@SpringBootApplication
public class RabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApplication.class, args);
        System.out.println("==>> rabbitmq测试demo已启动...");
    }
}
