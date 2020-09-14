package com.ysq.mybaitsproxy.interceptProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/14 19:52
 */
public class TargetProxyTwo implements InvocationHandler {

    Object target;
    Interceptor interceptor;

    private TargetProxyTwo(Object target, Interceptor interceptor){
        this.interceptor =interceptor;
        this.target = target;
    }

    public static Object getProxyObject(Object target, Interceptor interceptor){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new TargetProxyTwo(target, interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return interceptor.intercept(new Invocation(target, method, args));
    }
}
