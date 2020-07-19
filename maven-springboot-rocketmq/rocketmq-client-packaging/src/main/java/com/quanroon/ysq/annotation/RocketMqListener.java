package com.quanroon.ysq.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 普通消息监听
 *
 * @author ysq
 * @since 2020/7/19 15:25
 **/

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RocketMqListener {

    String value() default "";
}
