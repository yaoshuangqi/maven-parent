package com.ysq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @date 2020/3/3 10:43
 * @content
 */

@SpringBootApplication
public class springBootSwagger2 {

    private final static Logger logger = LoggerFactory.getLogger(springBootSwagger2.class);

    public static void main(String[] args) {

        SpringApplication.run(springBootSwagger2.class, args);
        logger.info("application running...");
    }
}
