package com.ysq.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/22 8:36
 */
@Component
public class HelloLifeCycle implements Lifecycle {
    @Override
    public void start() {
        System.out.println("==> lifeCycle start,");
    }

    @Override
    public void stop() {
        System.out.println("==> lifeCycle stop,");
    }

    @Override
    public boolean isRunning() {
        System.out.println("==> lifeCycle running....,");
        return false;
    }
}
