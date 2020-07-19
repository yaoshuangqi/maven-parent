package com.quanroon.ysq.enums;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 消息确认枚举
 * @createtime 2020/7/19 17:53
 */
public enum MqAction {

    //消费成功确认消息
    CommitMessage,

    //稍后继续消费
    ReconsumeLater;

    MqAction() {

    }
}
