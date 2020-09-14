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
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        if(invocation.getMethod().equals("work")){
            return invocation.proceed();
        }
        return null;
    }
}
