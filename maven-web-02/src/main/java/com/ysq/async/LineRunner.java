package com.ysq.async;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/21 16:37
 */
@Component
public class LineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("==> spring boot项目启动后，执行一些业务。。。。");
    }
}
