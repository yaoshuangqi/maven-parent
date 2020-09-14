package com.ysq.service;

import com.ysq.entity.Blog;

import java.util.List;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/8 23:18
 */
public interface BlogService2 {
    void delete(int id);

    List<Blog> getBlogList(Blog blog);
}
