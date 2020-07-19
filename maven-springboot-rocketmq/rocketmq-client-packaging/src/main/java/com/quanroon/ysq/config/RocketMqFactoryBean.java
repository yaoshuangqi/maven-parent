package com.quanroon.ysq.config;

import com.quanroon.ysq.mq.RocketMqComsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 创建生产者和消费者的工厂bean
 *
 * @author ysq
 * @since 2020/07/19 下午10:14
 **/
@Slf4j
public class RocketMqFactoryBean {

    @Bean
    public RocketMqComsumerService createConsumer(RocketMqProperties configuration,
        ApplicationContext context) throws Exception {
        return new RocketMqComsumerService(configuration, context);
    }

    /**
     * 创建生产者，并注入到容器
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public DefaultMQProducer defaultProducer(RocketMqProperties configuration) throws Exception {
        DefaultMQProducer producer = null;
        //是否开启生产者
        if(configuration.getProducer().getEnable()){
            if (configuration.getNamesrvAddr() == null) {
                throw new IllegalArgumentException("quanroon.rocketmq.producer.namesrvAddr 是必须的参数");
            }
            producer = new DefaultMQProducer(configuration.getProducer().getGroupName());
            producer.setNamesrvAddr(configuration.getNamesrvAddr());
            producer.setInstanceName(System.currentTimeMillis() + "");
            producer.start();

            log.info("rocketmq producer be created, of start success");
        }
        return producer;
    }


}
