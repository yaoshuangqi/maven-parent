package com.ysq.mybaitsproxy.simpleProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 代理类，持有目标类对象
 * @date 2020/9/14 16:49
 */
public class TargetProxy implements InvocationHandler {

    Object target;

    public TargetProxy(Object target){
        this.target =target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("==> 这是代理类处理的过程，我还能做什么，好孤单");
        return method.invoke(target, args);
    }
    /**
    * @Description: 获取代理类实例
    * @Author: quanroon.yaosq
    * @Date: 2020/9/14 17:00
    * @Param: [target]
    * @Return: java.lang.Object
    */
    public static Object getObjectProxy(Object target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new TargetProxy(target));
    }
}
