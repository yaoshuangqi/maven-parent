package com.quanroon.ysq.runner;

import com.quanroon.ysq.mq.RocketMqConsumerService;
import com.quanroon.ysq.mq.RocketMqProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/19 22:34
 */
@Component
public class MqApplicationRunner implements ApplicationRunner {

    @Autowired
    private RocketMqConsumerService consumerService;
    @Autowired
    private RocketMqProducerService producerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        producerService.start();
        consumerService.start();
    }
}
