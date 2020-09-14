package com.ysq.entity;

import com.ysq.entity.page.Page;
import lombok.Data;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/8 23:16
 */
@Data
public class Blog extends Page {

    private int user_id;
    private String user_name;
    private int user_age;
}
