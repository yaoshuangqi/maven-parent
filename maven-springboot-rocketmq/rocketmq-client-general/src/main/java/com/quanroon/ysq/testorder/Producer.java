package com.quanroon.ysq.testorder;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 顺序消息
 * @define 顺序消息是指消息的消费顺序和生产顺序相同，在某些场景下，必须保证顺序消息。
 * 比如订单的生成、付款、发货.顺序消息又分为全局顺序消息和部分顺序消息，全局顺序消息指某一个topic下的所有消息都要保证顺序；
 * 部分顺序消息只要保证某一组消息被顺序消费。对于订单消息来说，只要保证同一个订单ID的生成、付款、发货消息按照顺序消费即可
 *
 * 顺序消息原理：一句话，同一类消息发送相同的队列中即可。
 * @createtime 2020/7/25 15:09
 */
public class Producer {
    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("order-producer");
            producer.setNamesrvAddr("127.0.0.1:9876");

            producer.start();

            List<String> typeList = Arrays.asList("创建","支付","退款");//假设这里有三条消息需要进行生产，并按照此顺序来
            //局部顺序消息，已订单号来保证消息的入队列索引值相同
            String orderId = "NF"+ UUID.randomUUID().toString();//假设这是订单ID
            for (String type : typeList){
                String body = type +"  "+System.currentTimeMillis();
                Message msg = new Message("TopicOrder", body.getBytes());

                //发送数据:如果使用顺序消息,则必须自己实现MessageQueueSelector,保证消息进入同一个队列中去.
                SendResult sendResult = producer.send(msg,(mqs, msg1, arg)->{
                    int hashCode = arg.hashCode();
                    if(hashCode < 0){
                        hashCode = Math.abs(hashCode);
                    }
                    //根据订单ID对队列数取模，得到一个索引值，结果肯定小于队列数
                    hashCode = hashCode % mqs.size();
                    System.out.println("消息发送到队列索引值为："+hashCode);
                    return mqs.get(hashCode);
                },orderId);//指定topic下的队列下标
                //RocketMQ默认提供了两种MessageQueueSelector实现：随机/Hash
                //SendResult sendResult = producer.send(msg,new SelectMessageQueueByHash(),orderId);

                System.out.println("发送消息："+sendResult + " , body : " + body);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
