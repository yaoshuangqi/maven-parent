package com.quanroon.ysq.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 本类测试@Qualifier,@Primary,@Priority @Resource 依次按照先后顺序注入
 * 同时也测试注入list,map的属性
 * @createtime 2020/8/13 22:55
 */
@Component
public class WebSocketHandleContext implements ApplicationRunner {

    //Map存储所有的策略类，key为 @Component("xxx") 名称
    //@Qualifier,@Primary,@Priority @Resource 依次按照先后顺序注入
    @Autowired
    private Map<String, IWebSocketHandleStrategy> strategyMap;// = new ConcurrentHashMap<>();

    public WebSocketHandleContext() {
//        this.strategyMap.clear();
//        strategyMap.forEach((k, v) -> this.strategyMap.put(k, v));

        //根据spring注入此类时，调用此构造函数，因构造函数依赖其接口，会自动其实例化依赖对象，并完成注入
//        IWebSocketHandleStrategy strategy =  strategyMap.get("dust");
//        strategy.findDustList();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //根据spring注入此类时，调用此构造函数，因构造函数依赖其接口，会自动其实例化依赖对象，并完成注入
        strategyMap.forEach((k, v) ->{
            System.out.println("======> " + k);
            v.findDustList();
        });
    }

    /**
     * @param methodName   方法名称
     * @param projId       项目id
     * @param socketServer socket对象
     * @return
     * @description 通过反射调用策略模块下的方法
     * @Author lw
     * @date 2020/3/7 10:50
     */
//    public void pushMessage(Object socketServer, String methodName, Long projId) {
//        String strategyName = socketServer.module;
//        if (strategyMap.containsKey(strategyName)) {
//            //获取策略处理类
//            IWebSocketHandleStrategy strategy = strategyMap.get(strategyName);
//            try {
//                Object[] argspara = new Object[]{socketServer, projId};
//                //反射调用method方法
//                Reflections.invokeMethodByName(strategy, methodName, argspara);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            log.warn("没有创建名为{}的策略处理类", strategyName);
//        }
//
//    }
}
