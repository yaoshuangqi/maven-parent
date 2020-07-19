package com.quanroon.ysq.runner;

import com.quanroon.ysq.mq.RocketMqComsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MqApplicationRunner.class);

    @Autowired
    private RocketMqComsumerService comsumerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        comsumerService.start();
        LOGGER.info("mq consumer of start success");
    }
}
