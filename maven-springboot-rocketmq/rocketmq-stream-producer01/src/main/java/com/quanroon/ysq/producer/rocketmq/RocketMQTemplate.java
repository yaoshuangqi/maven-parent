package com.quanroon.ysq.producer.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description rocketmq模板，用生产者与消费者的创建
 * @createtime 2020/7/9 22:49
 */
@Component
public class RocketMQTemplate {

    DefaultMQProducer defaultMQProducer;
    DefaultMQPushConsumer consumer;

    @PostConstruct
    public void initMQ(){
        //初始化生产者
//        defaultMQProducer = new DefaultMQProducer("group_test1");
//        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");

//        consumer = new DefaultMQPushConsumer(rocketMQConfiguration.getConsumerGroupName());
//        consumer.setNamesrvAddr(rocketMQConfiguration.getConsumerNamesrvAddr());
//        consumer.subscribe(rocketMQConfiguration.getConsumerTopic(), "*");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.setConsumeTimestamp("20181109221800");
//        consumer.registerMessageListener(new AttenMessageListener());
    }
}
