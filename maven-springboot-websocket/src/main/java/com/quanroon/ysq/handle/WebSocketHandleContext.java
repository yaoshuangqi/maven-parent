package com.quanroon.ysq.handle;

import com.quanroon.ysq.service.IWebSocketHandleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/13 22:55
 */
@Component
public class WebSocketHandleContext {

    //Map存储所有的策略类，key为 @Component("xxx") 名称
    //@Autowired
    private final Map<String, IWebSocketHandleStrategy> strategyMap = new ConcurrentHashMap<>();

    public WebSocketHandleContext(Map<String, IWebSocketHandleStrategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach((k, v) -> this.strategyMap.put(k, v));

        //根据spring注入此类时，调用此构造函数，因构造函数依赖其接口，会自动其实例化依赖对象，并完成注入
        IWebSocketHandleStrategy strategy =  strategyMap.get("dust");
        strategy.findDustList();
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
