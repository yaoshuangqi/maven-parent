package com.ysq.service;

import com.ysq.entity.Blog;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/8 23:16
 */
public interface BlogService {
    void save(Blog blog);
    void update(Blog blog);
}
