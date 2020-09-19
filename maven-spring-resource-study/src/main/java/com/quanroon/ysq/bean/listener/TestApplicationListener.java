package com.quanroon.ysq.bean.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 这个事件contextRefreshedEvent，是当bean全部转载完成后，触发的事件。
 * 事件可以自定义，监听也可以自定义
 * @date 2020/8/20 17:22
 */
@Component
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("监听的哪个事件。。。"+contextRefreshedEvent);
    }
}
