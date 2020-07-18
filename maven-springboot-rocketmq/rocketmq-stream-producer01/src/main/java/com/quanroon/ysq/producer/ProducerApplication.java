package com.quanroon.ysq.producer;

import com.quanroon.ysq.producer.annotation.StreamOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description mq生产者启动器
 * @createtime 2020/7/5 13:14
 */
@EnableBinding({ StreamOutput.class })
@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
        System.out.println("rocketMQ生产者服务已启动==========>");
    }
}
