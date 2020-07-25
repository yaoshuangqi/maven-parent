package com.quanroon.ysq.config;

import lombok.Data;


/**
 * 定义消费者配置信息
 * @author ysq
 * @since 2020/07/19 下午10:14
 **/
@Data
public class ConsumerOrderProperties {

    //顺序消费者
    private String topic;
    private String tag = "*";
    private String groupName;

    /**
     * 是否开启顺序消费者配置(默认false)
     */
    private Boolean enable = Boolean.FALSE;
}
