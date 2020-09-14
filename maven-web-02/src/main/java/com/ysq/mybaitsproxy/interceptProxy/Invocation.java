package com.ysq.mybaitsproxy.interceptProxy;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 这个代理类和拦截器的连接的实例
 * @date 2020/9/14 17:28
 */
@Data
public class Invocation {

    private Object target;
    private Method method;
    private Object[] args;


    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    //判代理类的执行方法
    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target,args);
    }
}
