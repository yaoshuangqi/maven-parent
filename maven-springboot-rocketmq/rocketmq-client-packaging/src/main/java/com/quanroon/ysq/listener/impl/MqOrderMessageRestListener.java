package com.quanroon.ysq.listener.impl;


import com.quanroon.ysq.annotation.RocketMqOrderListener;
import com.quanroon.ysq.enums.MqAction;
import com.quanroon.ysq.listener.MessageOrderListener;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author lizhuo
 * @since 2019/1/5 下午9:27
 **/
@RocketMqOrderListener
public class MqOrderMessageRestListener implements MessageOrderListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqOrderMessageRestListener.class);


    @Override
    public MqAction consume(MessageExt message, ConsumeOrderlyContext context) {
        String msg = null;
        try {
            msg = new String(message.getBody(), "UTF-8");
            //处理消息业务逻辑
            LOGGER.info("MsgId:{},MQ消费,Topic:{},Tag:{}，Body:{}", message.getMsgId(),
                    message.getTopic(), message.getTags(), msg);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            LOGGER.error("unsupportedEncodingException of error ,",message);
        } catch (Exception e) {
            LOGGER.error("MsgId:{},应用MQ消费失败,Topic:{},Tag:{}，Body:{},异常信息:{}", message.getMsgId(),
                message.getTopic(), message.getTags(), msg, e);
            throw e;
        }
        return MqAction.CommitMessage;
    }


}
