package com.quanroon.ysq.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/16 18:13
 */
@Data
@ConfigurationProperties(prefix = "gp.redisson")
public class RedissonProperties {

    private String host = "localhost";
    private String password;
    private int port = 6379;
    private int timeout;
    private boolean ssl;
}
