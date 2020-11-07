package com.quanroon.ysq.web;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/10/24 17:07
 */
@RestController
public class RateLimiterController {

    @RequestMapping("rate")
    public Object testRateLimiter(){
        System.out.println("==> 当前接口限速1秒");
        return Collections.singletonMap("success", "true");
    }
}
