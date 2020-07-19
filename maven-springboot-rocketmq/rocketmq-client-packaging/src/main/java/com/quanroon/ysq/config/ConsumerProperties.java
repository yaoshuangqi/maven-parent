package com.quanroon.ysq.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;


/**
 * 定义消费者配置信息
 * @author ysq
 * @since 2020/07/19 下午10:14
 **/
@Data
public class ConsumerProperties {

    private String topic;
    private String tag = "*";
    private String groupName;

    /**
     * 是否开启生产者配置(默认false)
     */
    private Boolean enable = Boolean.FALSE;
}
