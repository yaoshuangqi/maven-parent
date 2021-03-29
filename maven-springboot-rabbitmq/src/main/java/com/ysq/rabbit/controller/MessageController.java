package com.ysq.rabbit.controller;

import com.ysq.rabbit.mq.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2021/3/29 22:05
 */
@RestController
public class MessageController {

    @Autowired
    private MessageProducer messageProducer;

    @RequestMapping("/sendMessage")
    public String sendMessage(){

        messageProducer.sendDelay("test delay message", 10000);
        System.out.println("-------发送时间："+new Date());
        return "SUCCESS";
    }
}
