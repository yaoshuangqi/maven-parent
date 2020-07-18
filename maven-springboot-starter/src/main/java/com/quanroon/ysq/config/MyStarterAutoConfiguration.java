package com.quanroon.ysq.config;

import com.quanroon.ysq.com.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 自动配置类
 * @createtime 2020/7/11 20:58
 */
@Configuration
@EnableConfigurationProperties(PersonConfigProperties.class)
@ConditionalOnClass(Person.class)
public class MyStarterAutoConfiguration {

    /**
     * 自动装配一个bean
     * ConditionalOnProperty：条件设置，只有设置为true才被注入
     * @param personConfigProperties
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "mystarter.config", name = "enable", havingValue = "true")
    public Person defaultStudent(PersonConfigProperties personConfigProperties) {
        Person person = new Person();
        person.setAge(personConfigProperties.getAge());
        person.setName(personConfigProperties.getName());
        person.setGender(personConfigProperties.getGender());
        return person;
    }
}
