package com.ysq.mybaitsproxy.interceptProxy;

import java.lang.reflect.InvocationTargetException;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 定义一个接口，来接管动态代理的执行方法，这样既可以增强代理类的功能，比如扩展执行其他方法
 * 可以被开发者拿去自己定义一个该类型的拦截器，并实现一些自己的业务，
 * 比如：在mybatis中也有这个一个拦截器。用于sql执行前，开发者可以自行实现该拦截器来处理一些额外的业务
 * @date 2020/9/14 17:17
 */
public interface Interceptor {

    Object register(Object target);

    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException;
}
