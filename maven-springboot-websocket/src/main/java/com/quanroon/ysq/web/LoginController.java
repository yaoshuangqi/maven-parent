package com.quanroon.ysq.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/10 22:14
 */
@Controller
public class LoginController {

    @RequestMapping("login")
    public String home(Model model) throws Exception {
        String userName = "Jef";
        int count = 100;
        System.out.println("==> 登陆成功");
        //ModelAndView mv = new ModelAndView("chat.html");
        return "chat";
    }
}
