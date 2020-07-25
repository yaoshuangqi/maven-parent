# 本项目开发的目的
用原生的时候每个消费者和生产者都需要重复编写启动代码，基于这个痛点，本作者进行封装，沉淀为基础架构
让程序员专注于业务开发。
##### 使用 以下以hello 服务来作为例子讲解
1. 在启动类HelloApplication上加上注解 @EnableRocketMq <br/>
2. 在配置文件上填写相关配置
 # mq 配置
        quanroon:
          rocketmq:
            # NameServer地址 用;作为地址的分隔符
            namesrv-addr: 192.168.136.128:9876
            # 生产者的组名
            producer:
              enable: true
              groupName: product-test
            consumer:
              #普通消费者
              enable: false
              topic: yaoshuangqi-topic
              group-name: consumer-test
              #顺序消费者
              order:
                enable: true
                topic: order-topic
                group-name: order-consumer-test
                 
3. 发布消息，在配置文件中开启生产者，直接启动服务
4. 监听消息请查看MqResultListener 类，监听消息只需要实现接口类MessageListener就可以，注意有顺序消息和无顺序消息不同，请查看注意事项说明
5. 普通消费者和顺序消费者，可直接在配置文件中配置


# 实现步骤
1、引用jar包，搭建框架，框架可直接生存start组件，或三方jar包，直接在项目中引入使用<br/>
2、采用注解模式，服务启动时，通过@Import将所需的配置参数和bena注入到容器，bean初始化完成后，根据配置信息启动对应的生产者或消费者


#注意事项：<br/>
1、消费有顺序消息时，请使用@RocketMqOrderListener 注解，并且实现接口类MessageOrderListener<br/>
2、消费普通消息时，请使用@RocketMqListener 注解，并且实现接口类MessageListener
3、本实例中的顺序消费，是基于消费者。还有一种



#链接：
https://github.com/clsaa/RocketMQ-Notes（mq中的幂等和消息去重，分布式事务处理）



