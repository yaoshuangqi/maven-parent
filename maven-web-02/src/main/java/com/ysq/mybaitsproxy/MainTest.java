package com.ysq.mybaitsproxy;

import com.ysq.mybaitsproxy.simpleProxy.TargerImpl;
import com.ysq.mybaitsproxy.simpleProxy.Target;
import com.ysq.mybaitsproxy.simpleProxy.TargetProxy;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/14 17:07
 */
public class MainTest {

    public static void main(String[] args) {
        Target objectProxy = (Target) TargetProxy.getObjectProxy(new TargerImpl());

        objectProxy.work();
    }
}
