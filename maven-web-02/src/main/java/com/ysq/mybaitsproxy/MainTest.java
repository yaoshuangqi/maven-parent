package com.ysq.mybaitsproxy;

import com.ysq.mybaitsproxy.interceptProxy.DefaultInterceptorImpl;
import com.ysq.mybaitsproxy.interceptProxy.TargetProxyTwo;
import com.ysq.mybaitsproxy.simpleProxy.TargerImpl;
import com.ysq.mybaitsproxy.simpleProxy.Target;
import com.ysq.mybaitsproxy.simpleProxy.TargetProxy;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/14 17:07
 */
public class MainTest {

    public static void main(String[] args) {
        //simpleProxy
        //这种方式，代理的类只能做一种事情，不能通过其他增强类来完成特定的任务，怎么办，看如下
//        Target objectProxy = (Target) TargetProxy.getObjectProxy(new TargerImpl());
//        objectProxy.work();

        //interceptProxy
        //代理类可以做被代理类的任务，也可以做这个任务前，做其他的任务，但是用户在使用时，需要知道其代理类和拦截器；那么可不可以把代理类加到拦截器中呢，看如下
        Target proxyObject = (Target)TargetProxyTwo.getProxyObject(new TargerImpl(), new DefaultInterceptorImpl());
        proxyObject.work();

        Target register = (Target)new DefaultInterceptorImpl().register(new TargerImpl());
        register.init();
    }
}
