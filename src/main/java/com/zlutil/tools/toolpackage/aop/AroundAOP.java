package com.zlutil.tools.toolpackage.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @desc: 经纪人切面
 * @author: CSH
 **/
@Aspect
@Component
public class AroundAOP {
    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.zlutil.tools.controller.*.*(..))")
    public void BrokerAspect() {

    }

    /**
     * @description  使用环绕通知
     */
    @Around("BrokerAspect()")
    public void doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
        try{
            System.out.println("经纪人正在处理球星赛前事务！!");
            pjp.proceed();
            System.out.println("返回通知：经纪人为球星表现疯狂鼓掌！");
        }
        catch(Throwable e){
            System.out.println("异常通知：球迷要求退票！");
        }
    }
}
