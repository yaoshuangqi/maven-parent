package com.ysq.define_resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 定义一个参数解析器需要的注解
 * @date 2020/11/7 11:53
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUser {
}
