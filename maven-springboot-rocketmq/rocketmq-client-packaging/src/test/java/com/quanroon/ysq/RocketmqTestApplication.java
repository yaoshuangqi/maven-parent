package com.quanroon.ysq;

import com.quanroon.ysq.mq.RocketMqProducerService;
import org.apache.tomcat.jni.Time;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/20 22:11
 */
@SpringBootTest(classes = RocketMqPackagingApplication.class)
@RunWith(SpringRunner.class)
public class RocketmqTestApplication {

    @Autowired
    private RocketMqProducerService producerService;

    @Test
    public void testCreateProducer() throws Exception{
        String topic = "yaoshuangqi-topic";

        producerService.synSend(topic,"","生产者"+ UUID.randomUUID().toString());
        Thread.sleep(1000000);
    }


}
