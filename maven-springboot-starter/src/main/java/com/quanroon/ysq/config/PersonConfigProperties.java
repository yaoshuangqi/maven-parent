package com.quanroon.ysq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 属性类
 * @createtime 2020/7/11 20:53
 */
@ConfigurationProperties(prefix = "mystarter.config.student")
@Data
public class PersonConfigProperties {
    private String name;
    private int age;
    private String gender;
}
