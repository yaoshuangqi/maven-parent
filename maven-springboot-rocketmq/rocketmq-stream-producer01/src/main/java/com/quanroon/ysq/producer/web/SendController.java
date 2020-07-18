package com.quanroon.ysq.producer.web;

import com.quanroon.ysq.producer.annotation.StreamOutput;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/5 13:18
 */
@RestController
public class SendController {

    /**
     * 在次强调，alibaba-cloud中自带的通道source有问题，最好不要使用
     * 这里我们自定义的一个发送通道
     */
    @Autowired
    private StreamOutput streamOutput;

    @GetMapping("/send")
    public String send(String msg){

        MessageBuilder messageBuilder = MessageBuilder.withPayload(msg);
        this.streamOutput.output().send(
                messageBuilder.build()
        );

        System.out.println("消息发送成功：" + msg);
        return "Hello RocketMQ Binder, send = " + msg;
    }



    @RequestMapping(value = "/sendTest", method = RequestMethod.GET)
    public void sendTest(String message) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer_group");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        //Message msg = new Message();
        //msg.setBody(message.getBytes());
        Message msg = new Message("springboot-mq", "test-tag", message.getBytes());
        producer.send(msg);

        //关闭
        producer.shutdown();
    }
}
