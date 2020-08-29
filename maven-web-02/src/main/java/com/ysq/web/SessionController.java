package com.ysq.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description session理解
 * @createtime 2020/8/28 21:34
 */
@RestController
public class SessionController {

    @Value("${server.port}")
    private String port;

    /**
    * @description 创建session会话
    * @author quanroong.ysq
    * @createtime 2020/8/28 22:52
    * @version 1.0.0
    */
    @RequestMapping("createSession")
    public String createSession(HttpServletRequest request){
        //request.getSession(true),默认为true,当如何没有session,会自动创建session,
        HttpSession session = request.getSession();
        String nameValue = "存储到session的name值："+UUID.randomUUID().toString();
        System.out.println("sessionId = " + session.getId() + ",nameValue = " + nameValue);

        session.setAttribute("name", nameValue);
        return "create session of success, 服务器port=" + request.getLocalPort();
    }

    /**
    * @description 获取session
    * @author quanroong.ysq
    * @createtime 2020/8/28 22:53
    * @version 1.0.0
    */
    @RequestMapping("getSession")
    public String getSession(HttpServletRequest request){

        HttpSession session = request.getSession();
        System.out.println("===> 执行端口="+request.getLocalPort());
        return "获取session中的name =" + session.getAttribute("name")+"服务器port="+request.getLocalPort();
    }
}
