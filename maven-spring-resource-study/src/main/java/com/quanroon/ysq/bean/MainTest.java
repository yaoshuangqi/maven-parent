package com.quanroon.ysq.bean;

import com.quanroon.ysq.bean.annoation.StudentServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GetBeanTest teacher = (GetBeanTest)context.getBean("getBeanTest");

        //自身bean
        teacher.getUserBean("testMyself");

        //meta元数据获取
        Object attribute = ((ClassPathXmlApplicationContext) context).getBeanFactory().getBeanDefinition("getBeanTest").getAttribute("keyTest");

        System.out.println("spring meta的值：" + attribute.toString());

        teacher.getChildUserByFactory().showMe();

        //teacher.showMe();
        //通过注解模式拿到
        StudentServiceImpl studentServiceImpl = (StudentServiceImpl)context.getBean("studentServiceImpl");

        studentServiceImpl.test();
    }
}
