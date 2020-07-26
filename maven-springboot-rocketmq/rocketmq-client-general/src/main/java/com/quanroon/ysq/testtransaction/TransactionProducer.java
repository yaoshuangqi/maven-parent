package com.quanroon.ysq.testtransaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.UUID;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 事务消息：即在发送消息时，先发送预处理消息到broker中，等到本地事务提交完成后，在提交消息确认发送
 * @createtime 2020/7/25 15:09
 */
public class TransactionProducer {
    public static void main(String[] args) throws MQClientException {
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("transaction-producer");
        transactionMQProducer.setNamesrvAddr("127.0.0.1:9876");

        //定义事务的监听
        transactionMQProducer.setTransactionListener(new TransactionListener(){

            //执行本地事务，并发送预处理消息
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //这个事务ID是每条消息中自动生成的，o为我们自定义的事务ID
                String transactionId = message.getTransactionId();
                String defineId = o.toString();
                System.out.println("执行事务："+transactionId + "=======" + defineId);

                //执行业务逻辑，如果失败，则回滚消息事务，如果成功，则提交事务

                //省略业务代码....

                return LocalTransactionState.COMMIT_MESSAGE;
            }

            //回查机制，当executeLocalTransaction方法中返回的状态未知或未返回状态时，默认一分钟后，broker将检查本地事务
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                //获取事务ID 这个事务ID是每条消息中自动生成的
                String transactionId = messageExt.getTransactionId();

                System.out.println("进行回查："+transactionId);
                //根据这个事务ID进行本地事务执行情况，

                //查询方式省略...

                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        transactionMQProducer.start();

        //本次发送的消息对应唯一的事务ID,这个跟业务挂钩。
        String transactionId = UUID.randomUUID().toString();

        //创建消息，并发送消息
        for (String type : new String[]{"你好","晚安啊","走起，我们一起发财"}){
            String body = type +"  "+System.currentTimeMillis();
            Message msg = new Message("TopicOrder", body.getBytes());

            //msg:消息体 arg:本地事务ID,用于回查确认消息
            transactionMQProducer.sendMessageInTransaction(msg,transactionId);
        }
    }
}
