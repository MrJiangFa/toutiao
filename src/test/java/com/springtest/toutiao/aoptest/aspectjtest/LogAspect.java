package com.springtest.toutiao.aoptest.aspectjtest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {
    //声明切点
    @Pointcut("@annotation(Action)")
    public void annotationPointCut(){};

    //声明一个建言，使用@PointCut定义的切点
    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

    }
    @Before("execution( * com.springtest.toutiao.aoptest.aspectjtest.DemoMethodService.*(..))")
    public void before(JoinPoint joinPoint){

    }
}
