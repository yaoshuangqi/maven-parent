package com.quanroon.ysq.listener;

import com.quanroon.ysq.enums.MqAction;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.common.message.MessageExt;


/**
 * 顺序消息监听
 *
 * @author ysq
 * @since 2020/07/19 下午10:14
 **/
public interface MessageOrderListener {

    /**
     * mq 消费接口
     */
    MqAction consume(MessageExt var1, ConsumeOrderlyContext context);
}
