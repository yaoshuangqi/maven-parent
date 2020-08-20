package com.quanroon.ysq.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/8/20 8:44
 */
@RestController
public class JmeterController {

    @RequestMapping(value = "/jmeter/test",method = RequestMethod.GET)
    public String test(){
        System.out.println("====> 测试中。。。");
        return "hello jmeter";
    }
}
