package com.ysq.mybaitsproxy.interceptProxy;

import java.lang.reflect.InvocationTargetException;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 默认拦截器的实现
 * @date 2020/9/14 17:33
 */
public class DefaultInterceptorImpl implements Interceptor {


    @Override
    public Object register(Object target) {
        return TargetProxyTwo.getProxyObject(target,this);
    }

    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        //判断是否是指定的方法名
        System.out.println("====>我是代理模式衍生出来的一个拦截器，用于执行一些特定的方法");
        if(invocation.getMethod().getName().equals("work")){
            return invocation.proceed();
        }
        return invocation.proceed();
    }
}
