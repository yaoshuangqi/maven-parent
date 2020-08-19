package com.ysq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/7/6 8:58
 */
@Data
@AllArgsConstructor
public class Greeting {
    private long id;
    private String content;

}
