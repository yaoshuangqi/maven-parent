package com.quanroon.ysq.bean;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

public class User implements MethodReplacer {

    public void showMe(){
        System.out.println("i an user");
    }


    /**
     * bean标签中repalce-method可以使用到，即可以替换原有的方法，用于业务变动
     * @param o
     * @param method
     * @param objects
     * @return
     * @throws Throwable
     */
    @Override
    public Object reimplement(Object o, Method method, Object[] objects) throws Throwable {
        return null;
    }
}
