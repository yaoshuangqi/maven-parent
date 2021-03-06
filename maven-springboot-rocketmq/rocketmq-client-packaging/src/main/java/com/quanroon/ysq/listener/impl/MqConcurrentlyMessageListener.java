package com.quanroon.ysq.listener.impl;


import com.quanroon.ysq.annotation.RocketMqListener;
import com.quanroon.ysq.enums.MqAction;
import com.quanroon.ysq.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author lizhuo
 * @since 2019/1/5 下午9:27
 **/
@RocketMqListener
public class MqConcurrentlyMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory
        .getLogger(MqConcurrentlyMessageListener.class);

    @Override
    public MqAction consume(MessageExt message, ConsumeConcurrentlyContext context) {
        String msg = null;
        try {
            msg = new String(message.getBody(), "UTF-8");

            LOGGER.info("======>同步消费：MsgId:{},MQ消费,Topic:{},Tag:{}，Body:{}", message.getMsgId(),
                message.getTopic(), message.getTags(), msg);

        } catch (UnsupportedEncodingException e1){
            e1.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("MsgId:{},应用MQ消费失败,Topic:{},Tag:{}，Body:{},异常信息:{}", message.getMsgId(),
                message.getTopic(), message.getTags(), msg, e);
            throw e;
        }
        return MqAction.CommitMessage;
    }


}
