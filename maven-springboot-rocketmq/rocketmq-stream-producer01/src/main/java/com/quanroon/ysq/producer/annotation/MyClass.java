package com.quanroon.ysq.producer.annotation;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/6 22:31
 */
public class MyClass {
    @MyAliasAnnotation(location = "这是值one")
    public static void one(){
    }

    @MyAliasAnnotation(value = "这是值two")
    public static void one2(){
    }
}
