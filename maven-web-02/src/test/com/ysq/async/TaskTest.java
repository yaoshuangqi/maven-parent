package com.ysq.async;

import com.ysq.Web2Application;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;
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
//        long start = System.currentTimeMillis();
//
//        task.doTaskOne();
//        task.doTaskTwo();
//
//        Thread.currentThread().join();

        String test = null;


    }

    public static void main(String[] args) throws IOException {
        Object[] objects = {new TaskTest(), "gg"};
        test(objects);
        List<String> officeIds = Lists.newArrayList();
        officeIds.add("323233212");
        officeIds.add("Defge232");
        String join = String.join(",", officeIds);

        System.out.println(join);

    }
    private static void test(Object... args){

        Object[] params = args;

        TaskTest dd = null;

        try {
            dd = (TaskTest)params[0];
            System.out.println(dd.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}