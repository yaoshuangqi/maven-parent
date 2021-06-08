package com.ysq.define_resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content handler参数解析器处理
 * @date 2020/11/7 11:54
 */
@Slf4j
@Component
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 指定参数如果被应用ResolverArg注解，则使用该解析器。
        return methodParameter.hasParameterAnnotation(ResolverArg.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        log.info("===>>>>自定义参数解析器");
        return null;
    }
}
