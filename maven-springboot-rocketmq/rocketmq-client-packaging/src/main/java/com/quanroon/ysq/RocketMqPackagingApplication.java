package com.quanroon.ysq;

import com.quanroon.ysq.annotation.EnableRocketMq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/19 15:13
 */
@SpringBootApplication
@EnableRocketMq
public class RocketMqPackagingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMqPackagingApplication.class, args);
        System.out.println("rocketmq server of success,please use of it....");
    }
}
