package com.quanroon.ysq.mq;

import com.quanroon.ysq.config.RocketMqProperties;
import com.quanroon.ysq.exception.MqSendException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description mq生产者服务类
 * @createtime 2020/7/19 17:53
 */
public class RocketMqProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqProducerService.class);

    private DefaultMQProducer rocketProducer = null;
    private RocketMqProperties configuration;
    private Boolean isEnable = Boolean.FALSE;

    public RocketMqProducerService(RocketMqProperties configuration) throws Exception{
        this.configuration = configuration;
        //是否开启生产者
        if(configuration.getProducer().getEnable()){
            if (configuration.getNamesrvAddr() == null) {
                throw new IllegalArgumentException("quanroon.rocketmq.producer.namesrvAddr 是必须的参数");
            }
            rocketProducer = new DefaultMQProducer(configuration.getProducer().getGroupName());
            rocketProducer.setNamesrvAddr(configuration.getNamesrvAddr());
            rocketProducer.setInstanceName(System.currentTimeMillis() + "");
            LOGGER.info("rocketmq producer be created");
        }
    }
    /**
     * 启动生产者
     * @throws Exception
     */
    public synchronized void start() throws Exception {
        if(this.configuration.getProducer().getEnable()){
            if (this.isEnable) {
                LOGGER.warn("RocketMQ生产者义启动");
                return;
            }
            rocketProducer.start();
            isEnable = true;
            LOGGER.info("======>rocketmq producer of start success");
        }
    }

    /**
    * 同步发送
    *
    * @param topic topic
    * @param tag tag
    * @param content 字符串消息体
    * @return 可能返回null
    */
    public SendResult synSend(String topic, String tag, String content) {
        return this.synSend(topic, tag, "", content);
    }


    /**
     * 同步发送
     *
     * @param topic topic
     * @param tag tag
     * @param content 字符串消息体
     * @return 可能返回null
     */
    public SendResult synSend(String topic, String tag, String keys, String content) {

        Message msg = getMessage(topic, tag, keys, content);
        try {
            SendResult sendResult = rocketProducer.send(msg);
            this.logMsg(msg, sendResult);
            return sendResult;
        } catch (Exception e) {
            LOGGER.error("同步发送消息失败", e);
            throw new MqSendException(e);
        }
    }

    /**
     * 构造message
     */
    public Message getMessage(String topic, String tag, String keys, String content) {
        return new Message(topic, tag, keys, content.getBytes());
    }

    /**
     * 打印消息topic等参数方便后续查找问题
     */
    private void logMsg(Message message, SendResult sendResult) {
        LOGGER.info("消息队列发送完成:topic={},tag={},msgId={},sendResult={}", message.getTopic(),
                message.getTags(), message.getKeys(), sendResult);
    }

}
