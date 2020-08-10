package com.quanroon.ysq.bean.annoation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/9 21:14
 */
@Component
public class StudentServiceImpl implements ApplicationContextAware {

    /**
     * 1、@Autowired 是通过 byType 的方式去注入的， 使用该注解，要求接口只能有一个实现类。
     * 2、@Resource 可以通过 byName 和 byType的方式注入， 默认先按 byName的方式进行匹配，如果匹配不到，再按 byType的方式进行匹配。
     * 3、@Qualifier 注解可以按名称注入， 但是注意是 类名。
     */

    private ApplicationContext applicationContext;

    public StudentServiceImpl(){
        System.out.println("看看在什么情况下走无参构造器。。。");
    }
    public StudentServiceImpl(String dd){
        System.out.println("这个是有参构造函数。。。");
    }

    public void test(){
        System.out.println("==> StudentServiceImpl of test class");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("激活Aware方法");
    }
}
