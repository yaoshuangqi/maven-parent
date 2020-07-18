package com.quanroon.ysq.producer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/11 9:11
 */
@Component
@EnableBinding(StreamInput.class)
public class ReceiveMessageListenerController {

    @StreamListener(StreamInput.input)
    public void input(Message<String> message){
        System.out.println("消费者1号，------->接收到的消息： "+message.getPayload());
    }
}
