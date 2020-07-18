package com.quanroon.ysq.producer.annotation;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAliasAnnotation {

    @AliasFor(value = "location")
    String value() default "";

    @AliasFor(value = "value")
    String location() default "";
}
