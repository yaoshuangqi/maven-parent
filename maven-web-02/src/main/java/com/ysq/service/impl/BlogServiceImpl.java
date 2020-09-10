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
        String sql = "update user_test1 set name = ? where id=?";
        jdbcTemplate.update(sql, new Object[]{blog.getUser_name(),blog.getUser_age()},
                new int[]{java.sql.Types.VARCHAR,java.sql.Types.INTEGER});
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertAfterDelete(){
       // Blog blog = jdbcTemplate.queryForObject("select * from user_test1 where user_id=19", Blog.class);
        Blog blog = new Blog();
        blog.setUser_id(10);
        blog.setUser_name("hell222o");
        String sql = "update user_test1 set user_name = ? where user_id=?";
        jdbcTemplate.update(sql, new Object[]{blog.getUser_name(),blog.getUser_id()},
                new int[]{java.sql.Types.VARCHAR,java.sql.Types.INTEGER});
        //使用insert后，无法使用required_new。会报锁超时
        //先增加后删除
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        PreparedStatementCreator preparedStatementCreator = con -> {
//            PreparedStatement ps = con.prepareStatement("insert into user_test1 VALUES (18,'测试费用名',120)", Statement.RETURN_GENERATED_KEYS);
//            return ps;
//        };
//
//        int update = jdbcTemplate.update(preparedStatementCreator, keyHolder);
//        System.out.println("=====> 先新增，后删除，"+update);
//
//        //在删除
//        blogService2.delete(keyHolder.getKey().intValue());



        blogService2.delete(blog.getUser_id());
        //this.delete1(blog.getUser_id());
    }
    //同一个方法类，使用Transactional,第二方法的事务会失效
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete1(int id){
        String sql = "delete from user_test1 where user_id=?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println("===> 你删除了了id="+id+"，看看="+update);

       // int i =1/0;
    }
}
