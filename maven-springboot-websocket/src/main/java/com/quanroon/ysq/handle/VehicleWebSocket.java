package com.quanroon.ysq.handle;

import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/13 22:57
 */
@Component("vehicle")
public class VehicleWebSocket implements IWebSocketHandleStrategy {

    /**
     * @param
     * @return
     * @description 查询所有车辆识别设备
     * @Author lw
     * @date 2020/3/7 11:47
     */
    public void findDustList() {
        System.out.println("==> 查询所有车辆识别设备");
    }
}
