package com.quanroon.ysq.bean.runner;

import com.quanroon.ysq.bean.listener.EmailEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/15 15:05
 */
public class EmailService implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //发送邮件是调用
    public void sendEmail(String numberAccount){
        EmailEvent emailEvent = new EmailEvent(numberAccount);
        //事件发布（即主动触发我自定义的事件）
        applicationContext.publishEvent(emailEvent);
    }
}
