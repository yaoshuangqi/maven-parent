package com.ysq.service.impl;

import com.ysq.entity.Blog;
import com.ysq.service.BlogService;
import com.ysq.service.BlogService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/8 23:21
 */
@Component
public class BlogServiceImpl implements BlogService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BlogService2 blogService2;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public void save(Blog blog) {
        //插入
        String sql = "insert into user_test1 values(?,?,?)";
        int update = jdbcTemplate.update(sql,
                new Object[]{blog.getUser_id(), blog.getUser_name(), blog.getUser_age()},
                new int[]{Types.INTEGER, Types.VARCHAR, Types.INTEGER});
        System.out.println("===> 增加完成了id="+blog.getUser_id()+",,影响了："+update);

    }

    @Override
    public void update(Blog blog) {
        String sql = "update blog set name = ? where id=?";
        jdbcTemplate.update(sql, new Object[]{blog.getUser_name(),blog.getUser_age()},
                new int[]{java.sql.Types.VARCHAR,java.sql.Types.INTEGER});
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertAfterDelete(){

        //先增加后删除
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement ps = con.prepareStatement("insert into user_test1 VALUES (18,'测试费用名',120)", Statement.RETURN_GENERATED_KEYS);
            return ps;
        };

        int update = jdbcTemplate.update(preparedStatementCreator, keyHolder);
        System.out.println("=====> 先新增，后删除，"+update);

        //在删除
        blogService2.delete(keyHolder.getKey().intValue());
    }
}
