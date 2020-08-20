package com.quanroon.ysq.bean.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 自定义一个事件  通过上下文触发自定义事件 webApplicationContext.publishEvent(event);
 * @date 2020/8/20 17:16
 */
public class EmailEvent extends ApplicationEvent {
    private String address;
    private String text;
    public EmailEvent(Object source, String address, String text){
    super(source);
    this.address = address;
    this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
