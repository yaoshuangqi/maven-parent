package com.ysq.mybaitsproxy.simpleProxy;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/14 16:47
 */
public class TargerImpl implements Target {
    @Override
    public void work() {
        System.out.println("===> 完犊子了，这是目标类的实现");
    }

    //这个方法，我不需要代理
    @Override
    public void init() {
        System.out.println("===> init params ,not be proxy");
    }
}
