package com.quanroon.ysq.producer.runner;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 对用那个中不方便直接注入的Bean,就可以使用此方法进行获取
 * @createtime 2020/7/19 14:20
 */
@Component
public class SpringJobBeanFactory implements ApplicationContextAware {

    //定义上下文环境对象
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringJobBeanFactory.applicationContext = applicationContext;
    }

    /**
     * 对外提供上下文环境对象
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取Bean实例
     * @param classNameType
     * @param <T>
     * @return
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> classNameType) throws BeansException{
        if(applicationContext == null)
            return null;
        return (T)applicationContext.getBean(classNameType);
    }
}
