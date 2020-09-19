package com.ysq.service.impl;

import com.ysq.entity.Blog;
import com.ysq.service.BlogService2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description BeanPostProcessor处理器，spring中开放式架构，能够让用户在bean装载时进行扩展，
 * 注意：是在调用客户自定义初始化方法前以及调用自定义初始化方法之后分别调用接口中对应的方法
 * @createtime 2020/9/8 23:21
 */
@Component
public class BlogServiceImpl2 implements BlogService2, BeanFactoryPostProcessor, BeanPostProcessor {

    public BlogServiceImpl2(){
        System.out.println("--0------>BlogServiceImpl2BlogServiceImpl2BlogServiceImpl2");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    //@Transactional(propagation = Propagation.MANDATORY)
    public void delete(int id) {
        String sql = "delete from user_test1 where user_id=?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println("===> 你删除了了id="+id+"，看看="+update);

        //int i =1/0;
    }

    @Override
    public List<Blog> getBlogList(Blog blog) {
        return null;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("---> MyBeanFactoryPostProcessor.....");
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (int i = 0; i <beanDefinitionNames.length ; i++) {
            System.out.println("+++++> bean:"+beanDefinitionNames[i]);
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
