package com.ysq.rabbit.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2021/3/29 22:21
 */
@Component
@RabbitListener(queues = "my-dlx-queue")
public class MessageConsumer {

    @RabbitHandler
    public void recieve(Map<String, String> message){
        System.out.println("---------"+new Date());

        System.out.println("接收到的消息："+message.get("msg"));
    }
}
