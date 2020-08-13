package com.quanroon.ysq.service;

import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/13 22:57
 */
@Component("dust")
public class DustWebSocket implements IWebSocketHandleStrategy {

    /**
     * @param
     * @return
     * @description 查询所有环境监控设备
     * @Author lw
     * @date 2020/3/7 11:47
     */
    public void findDustList() {
        System.out.println("==> 查询所有环境监控设备");
    }
}
