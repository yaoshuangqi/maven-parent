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
        rabbitTemplate.convertAndSend("delayQueue", map, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置过期时间，单位毫秒
                message.getMessageProperties().setExpiration(delay.toString());
                return message;
            }
        });

    }
}
