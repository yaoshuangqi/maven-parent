package com.quanroon.ysq.bean;

public class Student extends User {

    public void showMe(){
        System.out.println(" i am student");
    }

    public void initTest(){
        System.out.println("当Student这个bean在初始化时，执行此方法。。。。。");
    }
}
