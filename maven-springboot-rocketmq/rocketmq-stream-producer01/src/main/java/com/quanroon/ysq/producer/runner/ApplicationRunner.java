package com.quanroon.ysq.producer.runner;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 *      ApplicationRunner:当spring容器完成实例化后，进行一些参数的初始化
 *      ApplicationContextAware：获取spring容器中的上下文环境对象，并通过该对象获取容器的Bean,应用的场景就是：比如我们在开发中有些地方需要spring
 *      容器中的bean,而又不能直接注入进行来，于是就可以使用这个对象来注入。像Utils类中的Bean,一般我都是设置成static，就没法直接注入了。
 *      （不过这里可以用@PostContrucat注解初始化）
 * @createtime 2020/7/9 22:27
 */
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner, ApplicationContextAware {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("当spring容器完成实例化后，进行一些参数初始化设置。。。");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("设置applicationContext内容。。。。。");
    }
}
