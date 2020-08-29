package com.ysq.config;

import com.ysq.Web2Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 如果打war,则需要配置这个servlet初始化类
 * @createtime 2020/8/29 16:27
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Web2Application.class);
    }
}
