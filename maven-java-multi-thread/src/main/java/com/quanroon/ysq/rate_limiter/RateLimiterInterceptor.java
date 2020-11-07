package com.quanroon.ysq.rate_limiter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/10/24 17:00
 */
public class RateLimiterInterceptor extends HandlerInterceptorAdapter {

    private final  RateLimiter rateLimiter;

    public RateLimiterInterceptor(RateLimiter rateLimiter){
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //非阻塞获取令牌
        if(this.rateLimiter.tryAcquire()){
            return true;
        }
        /**
         * 获取失败，直接响应“错误信息”
         * 也可以通过抛出异常，通过全全局异常处理器响应客户端
         */
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().write("服务器繁忙.您访问的太快了，有点承受不了了，哈哈");
        return false;
    }
}
