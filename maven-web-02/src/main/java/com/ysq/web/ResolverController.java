package com.ysq.web;

import com.ysq.define_resolver.ResolverArg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yaoShuangQi
 * @description 测试自定义解析器
 * @date 2021/6/7 16:39
 */
@RestController
public class ResolverController {

    /**
     * @desc 注意：此参数不能和spring内置的
     * @author yaoShuangQi
     * @date 2021/6/7 17:22
     */
    @RequestMapping("testResolver")
    public String createSession(@ResolverArg String orgId){
        System.out.println("=--==>测试自定义参数解析器");
        return "success";
    }
}
