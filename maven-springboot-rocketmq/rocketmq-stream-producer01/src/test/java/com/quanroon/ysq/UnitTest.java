package com.quanroon.ysq;

import com.quanroon.ysq.producer.annotation.MyAliasAnnotation;
import com.quanroon.ysq.producer.annotation.MyClass;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/6 22:33
 */

public class UnitTest {
    @Test
    public void test() throws NoSuchMethodException{
        Consumer<MyAliasAnnotation> logic = a ->{
            System.out.println(a.value() +"========="+a.location());
            //Assert.assertTrue("比较失败", "这是值one".equals(a.value()));
            //Assert.assertTrue("111111", a.value().equals(a.location()));
        };

        MyAliasAnnotation aliasAnnotation = AnnotationUtils.findAnnotation(MyClass.class.getMethod("one"), MyAliasAnnotation.class);
        logic.accept(aliasAnnotation);

        MyAliasAnnotation aliasAnnotation2 = AnnotationUtils.findAnnotation(MyClass.class.getMethod("one2"), MyAliasAnnotation.class);
        logic.accept(aliasAnnotation2);
    }

    @Test
    public void mqTest() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("test_producer_group");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        String message = UUID.randomUUID().toString();//消息
        Message msg = new Message("springboot-mq", "test-tag", message.getBytes());
        producer.send(msg);

        //关闭
        producer.shutdown();
    }
}
