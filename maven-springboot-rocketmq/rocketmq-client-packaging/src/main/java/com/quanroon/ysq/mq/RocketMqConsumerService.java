package com.quanroon.ysq.mq;

import com.google.common.collect.Maps;
import com.quanroon.ysq.annotation.RocketMqListener;
import com.quanroon.ysq.annotation.RocketMqOrderListener;
import com.quanroon.ysq.config.RocketMqProperties;
import com.quanroon.ysq.enums.MqAction;
import com.quanroon.ysq.listener.MessageListener;
import com.quanroon.ysq.listener.MessageOrderListener;
import com.quanroon.ysq.utils.GeneratorId;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description mq消费者服务类
 * @createtime 2020/7/19 17:53
 */
public class RocketMqConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqConsumerService.class);

    public ApplicationContext context;
    private volatile boolean init = false;
    private RocketMqProperties configuration;
    private Map<String, DefaultMQPushConsumer> consumerMap;


    public RocketMqConsumerService(RocketMqProperties configuration, ApplicationContext context) {
        this.context = context;
        this.configuration = configuration;
    }

    /**
     * 启动消费者
     * @throws Exception
     */
    public synchronized void start() throws Exception {
        if (this.init) {
            LOGGER.warn("请不要重复初始化RocketMQ消费者");
            return;
        }
        this.consumerMap = Maps.newConcurrentMap();
        initializeConsumer(this.consumerMap);
        init = true;
        LOGGER.info("======>rocketmq consumer of start success");
    }

    /**
     * 初始化消费者，同项目内不允许对同一个topic多次加载
     *
     * @param map 存储消费者
     */
    private void initializeConsumer(Map<String, DefaultMQPushConsumer> map) throws Exception {

        Map<String, String> topicMap = Maps.newHashMap();

        Map<String, String> consumerGroupMap = Maps.newHashMap();
        //初始化普通消息消费者
        if(this.configuration.getConsumer().getEnable()) {
            initializeConcurrentlyConsumer(map, topicMap, consumerGroupMap);
        }
        //初始化有序消息消费者
        if(this.configuration.getConsumer().getOrder().getEnable()) {
            initializeOrderConsumer(map, topicMap, consumerGroupMap);
        }

        consumerMap.forEach((key, consumer) -> {
            try {
                String instanceName = System.currentTimeMillis() + GeneratorId.nextFormatId();
                consumer.setInstanceName(instanceName);
                consumer.start();
                LOGGER.info(String.format("自建RocketMQ 成功加载 Topic-tag:%s", key));
            } catch (MQClientException e) {
                LOGGER.error(String.format("自建RocketMQ 加载失败 Topic-tag:%s", key), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        LOGGER.info("--------------成功初始化所有消费者到自建mq--------------");

    }

    /**
     * 初始化普通消息消费者
     */
    private void initializeConcurrentlyConsumer(Map<String, DefaultMQPushConsumer> map,
                                                Map<String, String> topicMap, Map<String, String> consumerGroupMap)
            throws MQClientException {
        Map<String, Object> beansWithAnnotationMap = context
                .getBeansWithAnnotation(RocketMqListener.class);

        //配置信息
        String topic = this.configuration.getConsumer().getTopic();
        String tag = this.configuration.getConsumer().getTag();
        String consumerGroup = this.configuration.getConsumer().getGroupName();

        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            // 获取到实例对象的class信息
            Class<?> classIns = entry.getValue().getClass();

            //校验参数配置
            validate(topicMap, consumerGroupMap, classIns, topic, consumerGroup);

            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
            //设置名称服务器地址
            consumer.setNamesrvAddr(this.configuration.getNamesrvAddr());
            consumer.subscribe(topic, tag);
            //注册消费回调
            consumer.registerMessageListener((MessageListenerConcurrently) (msgList, context) -> {
                try {
                    for (MessageExt msg : msgList) {
                        MessageListener listener = (MessageListener) entry.getValue();
                        MqAction action = listener.consume(msg, context);
                        switch (action) {
                            case ReconsumeLater:
                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            default:
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("消费失败", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            map.put(String.format("%s-%s", topic, tag), consumer);
        }
    }

    /**
     * 初始化有序消息消费者
     */
    private void initializeOrderConsumer(Map<String, DefaultMQPushConsumer> map,
                                         Map<String, String> topicMap, Map<String, String> consumerGroupMap)
            throws MQClientException {
        Map<String, Object> beansWithAnnotationMap = context
                .getBeansWithAnnotation(RocketMqOrderListener.class);

        //配置信息
        String topic = this.configuration.getConsumer().getOrder().getTopic();
        String tag = this.configuration.getConsumer().getOrder().getTag();
        String consumerGroup = this.configuration.getConsumer().getOrder().getGroupName();

        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            // 获取到实例对象的class信息
            Class<?> classIns = entry.getValue().getClass();
            //校验参数配置
            validate(topicMap, consumerGroupMap, classIns, topic, consumerGroup);

            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
            //设置名称服务器地址
            consumer.setNamesrvAddr(this.configuration.getNamesrvAddr());
            consumer.subscribe(topic, tag);
            //注册消费回调
            consumer.registerMessageListener((MessageListenerOrderly) (msgList, context) -> {

                try {
                    for (MessageExt msg : msgList) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("consume msg={}", msg);
                        }
                        MessageOrderListener listener = (MessageOrderListener) entry.getValue();
                        MqAction action = listener.consume(msg, context);
                        switch (action) {
                            case ReconsumeLater:
                                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                            default:
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("消费失败", e);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            });

            map.put(String.format("%s-%s", topic, tag), consumer);
        }
    }

    /**
     * 校验参数配置
     * @param topicMap
     * @param consumerGroupMap
     * @param classIns
     * @param topic
     * @param consumerGroup
     */
    private void validate(Map<String, String> topicMap, Map<String, String> consumerGroupMap,
                          Class<?> classIns, String topic, String consumerGroup) {
        if (StringUtils.isBlank(topic)) {
            throw new RuntimeException(classIns.getSimpleName() + ":topic不能为空");
        }
        if (StringUtils.isBlank(consumerGroup)) {
            throw new RuntimeException(classIns.getSimpleName() + ":consumerGroup不能为空");
        }

        if (topicMap.containsKey(topic)) {
            throw new RuntimeException(
                    String.format("Topic:%s 已经由%s监听 请勿重复监听同一Topic", topic, classIns.getSimpleName()));
        }

        if (consumerGroupMap.containsKey(consumerGroup)) {
            throw new RuntimeException(String
                    .format("consumerGroup:%s 已经由%s监听 请勿重复监听同一consumerGroup", consumerGroup,
                            classIns.getSimpleName()));
        }
    }

}
