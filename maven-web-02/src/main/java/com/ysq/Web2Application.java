package com.ysq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description  @EnableRedisHttpSession注解启动session共享
 * @createtime 2020/8/28 21:43
 */
@SpringBootApplication
//@EnableRedisHttpSession
public class Web2Application {

    public static void main(String[] args) {
        SpringApplication.run(Web2Application.class, args);
    }
}
