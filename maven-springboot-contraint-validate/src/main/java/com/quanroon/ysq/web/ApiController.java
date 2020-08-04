package com.quanroon.ysq.web;

import com.quanroon.ysq.vo.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/14 21:57
 */
@RestController
public class ApiController {

    @RequestMapping(value = "createUser", method = RequestMethod.POST)
    public String createUser(@RequestBody @Valid User user, BindingResult bindingResult){
        System.out.println(user.toString());
        return "success";
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public String getUser(){
        System.out.println("getUser==========>");
        return "success";
    }

    /**
     * 测试aop
     * @param user
     * @return
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.GET)
    public String updateUser(User user){
        user.setAge(55);
        user.setSex("0");
        System.out.println("getUser==========>"+user.toString());
        //int i = 1/0;
        add(555L);
        return "success";
    }

    private String add(Long userId){
        System.out.println("=======>"+userId);
        return "dddfdf";
    }

}
