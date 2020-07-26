package com.quanroon.ysq.testorder;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 顺序消费
 * @createtime 2020/7/25 16:13
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order-producer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("TopicOrder", "*");
        //MessageListenerOrderly保证顺序消费，因为在OrderlyService中给每一个 Queue加锁了。消费者进行消费前，都需要进行获取此锁。
        consumer.registerMessageListener((MessageListenerOrderly)(msgList, context) ->{

            for (MessageExt msg : msgList){
                System.out.println("消费：content : "+ new String(msg.getBody()));
            }
            try {
                Random random = new Random();
                //模拟业务处理
                TimeUnit.SECONDS.sleep(random.nextInt(4));
            }catch (Exception e){
                e.printStackTrace();
                return  ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        //启动消费者
        consumer.start();
        System.out.println("顺序消费者启动成功....");
    }
}
