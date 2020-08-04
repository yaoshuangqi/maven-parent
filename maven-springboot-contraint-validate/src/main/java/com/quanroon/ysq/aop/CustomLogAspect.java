package com.quanroon.ysq.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 自定义切面
 * @createtime 2020/8/3 21:56
 */
@Component
@Aspect
@Slf4j
public class CustomLogAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.quanroon.ysq.web.ApiController.updateUser(..))")
    public void pointCut(){}

    /**
     * 定义前置通知
     * @param joinPoint
     * @throws Throwable
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        log.info("第二步，【注解：Before】------------------切面  before，所带参数为："+joinPoint.getArgs()[0]);
    }

    /**
     * 定义后置通知
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     * @param joinPoint
     * @throws Throwable
     */
    @After("pointCut()")
    public void after(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        log.info("第四步，【注解：after】-----一定会执行-------------切面  after，所带参数为："+joinPoint.getArgs()[0]);
    }


    /**
     * @Description: 后置返回通知
     * @Title: afterReturning
     * @author OnlyMate
     * @Date 2018年9月10日 下午3:52:30
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public String afterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("第五步，【注解：AfterReturning】这个会在切面最后的最后打印，方法的返回值 : " + ret + ", 参数为："+joinPoint.getArgs()[0]);

        return "ggg-hhhh";
    }

    /**
     * @Description: 后置异常通知
     * @Title: afterThrowing
     * @author OnlyMate
     * @Date 2018年9月10日 下午3:52:37
     * @param jp
     */
    @AfterThrowing("pointCut()")
    public void afterThrowing(JoinPoint jp){
        log.info("【注解：AfterThrowing】方法异常时执行.....");
    }


    /**
     * @Description: 环绕通知,环绕增强，相当于MethodInterceptor
     * @Title: around
     * @author OnlyMate
     * @Date 2018年9月10日 下午3:52:56
     * @param pjp
     * @return
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        log.info(" 第一步，【注解：Around . 环绕前】方法环绕start.....");
        try {
            //如果不执行这句，会不执行切面的Before方法及controller的业务方法
            Object o =  pjp.proceed();//返回的是被切类的返回值
            log.info("第三步，【注解：Around. 环绕后】方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
