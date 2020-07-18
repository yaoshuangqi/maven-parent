package com.quanroon.ysq.producer.service;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/11 9:20
 */
public interface IMessageProvider {
    String send();

    default void defaltSend(){
        //默认实现方法
        send();
    }
}
