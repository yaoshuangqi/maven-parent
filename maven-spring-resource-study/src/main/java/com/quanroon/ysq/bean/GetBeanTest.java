package com.quanroon.ysq.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 创建一个调用方法，用于获取bean
 */
public abstract class GetBeanTest {

    //@Resource(name = "student")
    @Autowired
    @Qualifier("tt")
    private User user;
    /**
     * 获取User接口中的方法
     */
    public void showMe(){
        this.getUserBean().showMe();
    }


    public User getChildUserByFactory(){
        return this.getUserBean();
    }

    //声明一个抽象父类方法，用于获取其子类的实例
    public User getUserBean(String d){
        System.out.println("没有写任何东西的方法，还不如定义一个抽象方法，这个方法只是过渡的，根本不会打印出任何东西" +
                "实际走的是xml中定义的bean值得实例");
        System.out.println(d+"----");
        user.showMe();
        return null;
    }
    //声明一个抽象父类方法，用于获取其子类的实例
    public abstract User getUserBean();
}
