package com.ysq.define_resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc 定义一个参数解析器需要的注解
 * @author yaoShuangQi
 * @date 2021/6/7 16:31
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResolverArg {
}
