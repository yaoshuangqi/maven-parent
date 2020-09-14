package com.ysq.service.impl;

import com.ysq.entity.Blog;
import com.ysq.service.BlogService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/8 23:21
 */
@Component
public class BlogServiceImpl2 implements BlogService2 {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    //@Transactional(propagation = Propagation.MANDATORY)
    public void delete(int id) {
        String sql = "delete from user_test1 where user_id=?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println("===> 你删除了了id="+id+"，看看="+update);

        //int i =1/0;
    }

    @Override
    public List<Blog> getBlogList(Blog blog) {
        return null;
    }
}
