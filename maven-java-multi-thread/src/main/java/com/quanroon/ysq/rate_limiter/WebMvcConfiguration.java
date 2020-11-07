package com.quanroon.ysq.rate_limiter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/10/24 17:04
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1秒钟生成1个令牌，也就是1秒中允许一个人访问
        registry.addInterceptor(new RateLimiterInterceptor(RateLimiter.create(1, 2, TimeUnit.SECONDS)))
                .addPathPatterns("/rate");
    }
}
