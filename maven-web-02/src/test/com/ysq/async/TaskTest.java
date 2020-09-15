package com.ysq.async;

import com.ysq.Web2Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content
 * @date 2020/9/15 10:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Web2Application.class)
public class TaskTest {

    @Autowired
    Task task;



    @Test
    public void doTaskOne() throws Exception{
        long start = System.currentTimeMillis();

        task.doTaskOne();
        task.doTaskTwo();

        Thread.currentThread().join();
    }
}