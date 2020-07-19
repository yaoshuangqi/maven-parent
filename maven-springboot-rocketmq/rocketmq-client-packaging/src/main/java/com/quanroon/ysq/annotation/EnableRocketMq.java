package com.quanroon.ysq.annotation;

import com.quanroon.ysq.config.RocketMqFactoryBean;
import com.quanroon.ysq.config.RocketMqProperties;
import com.quanroon.ysq.mq.RocketMqComsumerService;
import com.quanroon.ysq.mq.RocketMqProducerService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用mq 注解
 *
 * @author ysq
 * @since 2020/7/19 15:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        RocketMqProperties.class,
        RocketMqFactoryBean.class,
        RocketMqProducerService.class,
        RocketMqComsumerService.class
})
public @interface EnableRocketMq {
}
