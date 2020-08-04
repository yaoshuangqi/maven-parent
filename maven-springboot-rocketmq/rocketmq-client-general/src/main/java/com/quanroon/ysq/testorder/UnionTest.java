package com.quanroon.ysq.testorder;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.Arrays;
import java.util.List;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/26 16:34
 */
public class UnionTest {

    public static void main(String[] args) throws Exception {
        mq();
    }

    //rocketmq测试
    public static void mq() throws Exception {
        String topic = "order-test";
        String nameAddr = "127.0.0.1:9876";
        DefaultMQProducer producer = new DefaultMQProducer("order-producer");
        producer.setNamesrvAddr(nameAddr);
        //是否开启故障延迟机制（默认false）
        producer.setSendLatencyFaultEnable(true);

        producer.start();

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order-producer");
        consumer.setNamesrvAddr(nameAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(topic, "*");


        //注册监听
        consumer.registerMessageListener((MessageListenerOrderly) (msgList, context) ->{

            for (MessageExt msg : msgList){

                try {
                    Thread.sleep(2000);
                    //测试消息消费重试，当消息为退款，直接发生异常
                    if("TK".equals(msg.getTags())){
                        //退款消费发生异常
                        //int i = 1/0;
                    }

                    System.out.println("进行消费：content : "+ new String(msg.getBody()));
                } catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println("退款消费发生异常,重试次数："+msg.getReconsumeTimes());
                    if(msg.getReconsumeTimes() == 5){
                        //当重试消费次数达到5次后，不再进行重试消费，可以将消息持久化存储到DB或LOG日志中
                        return ConsumeOrderlyStatus.SUCCESS;
                    }
                    return null;
                }
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });

        //启动消费者
        consumer.start();
        System.out.println("顺序消费者启动成功....");
        Thread.sleep(3000);

        //发送消息
        sendMsg(producer, topic);
    }

    public static void sendMsg(DefaultMQProducer producer, String topic) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        List<String> typeList = Arrays.asList("创建","支付","退款","完成========");//假设这里有三条消息需要进行生产，并按照此顺序来
        String orderId = "NF3453889341";//假设这是订单ID
        for(int i = 0; i < 100; i++){

            for (String type : typeList){
                String body = type +"  "+System.currentTimeMillis();

                Message msg = new Message(topic, body.getBytes());

                if("退款".equals(type)){
                    msg.setTags("TK");
                }
                //顺序消息发送
                SendResult sendResult = producer.send(msg,(mqs, msg1, arg)->{
                    int index = arg.hashCode();
                    if(index < 0)
                        index = Math.abs(index);
                    //指定topic下的队列下标
                    index = index % mqs.size();
                    return mqs.get(index);
                },orderId);
                //同步发送
                //SendResult sendResult = producer.send(msg);

                System.out.println("发送消息："+sendResult + " , body : " + body);
                Thread.sleep(1000);
            }
        }
    }
}
