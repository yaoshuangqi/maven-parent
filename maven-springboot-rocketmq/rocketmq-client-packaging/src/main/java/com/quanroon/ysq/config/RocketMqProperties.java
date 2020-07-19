package com.quanroon.ysq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;


/**
 * rocketmq 的配置文件
 *
 * @author ysq
 * @since 2020/07/19 下午10:14
 **/
@Data
@ConfigurationProperties(prefix = "quanroon.rocketmq")
@Order(-1)
public class RocketMqProperties {
    /**
     * rocketmq集群名称服务地址，用;作为地址的分隔符
     */
    private String namesrvAddr;
    private ProducerProperties producer = new ProducerProperties();
    private ConsumerProperties consumer = new ConsumerProperties();


}
