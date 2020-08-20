package com.quanroon.ysq.bean.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content ApplicationContext事件机制是观察者设计模式的实现，通过ApplicationEvent类和ApplicationListener接口，可以实现ApplicationContext事件处理。
 *
 *  如果容器中有一个ApplicationListener Bean，每当ApplicationContext发布ApplicationEvent时，ApplicationListener Bean将自动被触发。这种事件机制都必须需要程序显示的触发。
 *
 * 其中spring有一些内置的事件，当完成某种操作时会发出某些事件动作。比如监听ContextRefreshedEvent事件，当所有的bean都初始化完成并被成功装载后会触发该事件，实现ApplicationListener<ContextRefreshedEvent>接口可以收到监听动作，然后可以写自己的逻辑。
 *
 * 同样事件可以自定义、监听也可以自定义，完全根据自己的业务逻辑来处理。
 *
 * Spring 内置事件 & 描述 ：   ContextRefreshedEvent  ContextStartedEvent  ContextStoppedEvent  ContextClosedEvent  RequestHandledEvent
 *
 * https://blog.csdn.net/liyantianmin/article/details/81017960
 * @date 2020/8/20 17:16
 */
@Component
public class EmailNotifierListener implements ApplicationListener<EmailEvent> {
    @Override
    public void onApplicationEvent(EmailEvent event) {
        if (event instanceof EmailEvent) {
            EmailEvent emailEvent = (EmailEvent)event;
            System.out.println("邮件地址：" + emailEvent.getAddress());
            System.out.println("邮件内容：" + emailEvent.getText());
        } else {
            System.out.println("容器本身事件：" + event);
        }
    }
}
