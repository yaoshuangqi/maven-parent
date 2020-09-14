package com.ysq.web;

import com.ysq.entity.Blog;
import com.ysq.service.BlogService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/14 14:08
 */
@RestController
public class PagePluginController {

    @Autowired
    BlogService2 blogService2;

    @RequestMapping(value = "getList")
    public String geBlogListByPage(Blog blog){

        blog.openPage();//开启分页

        List<Blog> blogList = blogService2.getBlogList(blog);

        return "success";
    }
}
