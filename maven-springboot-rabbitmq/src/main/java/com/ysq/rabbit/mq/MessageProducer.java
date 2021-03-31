package com.ysq.rabbit.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2021/3/29 22:04
 */
@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelay(String message, Integer delay){
        Map<String, String> map = new HashMap();
        map.put("msg", message);
        rabbitTemplate.convertAndSend("delayQueue", map, message1 -> {
            //设置过期时间，注意：如果是手写延迟队列，则使用setExpiration,如果采用延迟队列插件，则使用setDelay
            message1.getMessageProperties().setExpiration(delay.toString());
            return message1;
        });

    }
}
