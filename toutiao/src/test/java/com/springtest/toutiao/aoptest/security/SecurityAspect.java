package com.springtest.toutiao.aoptest.security;

import com.springtest.toutiao.aoptest.Service.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {
    @Autowired
    AuthService authService;

    @Pointcut("@annotation(com.springtest.toutiao.aoptest.security.AdminOnly)")
    public void matchAnnotation() {
    }

    @Pointcut("bean(productService)")
    public void matchAnno() {
    }

//    @Before("adminOnly()")
//    public void check() {
////        authService.checkAccess();//执行认证过程
//        System.out.println("认证通过");
//    }

    //修饰符 返回值类型 包名
    @Pointcut("execution(public * com.springtest.toutiao.aoptest.Service.*Service.*(..)) ")
    public void matchCondition() {
    }

    @Pointcut("execution(String com.springtest.toutiao.aoptest.Service..*Service*.*(..))")
    public void matchReturn() {
    }

    @AfterReturning(value = "matchReturn()", returning = "result")
    public void after(Object result) {
        System.out.println("after result:" + result);
    }

    @Around("matchAnnotation()")
    public Object after(ProceedingJoinPoint joinPoint) {
        System.out.println("before");
        Object res = null;
        try {
            res = joinPoint.proceed(joinPoint.getArgs());
            System.out.println("after returning"+res.toString());
        } catch (Throwable e) {
            System.out.println("exception");
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
        return res;
    }

//    @Before("matchCondition()")
//    public void judgeMathchCondition(){
//        System.out.println("执行execution表达式！");
//    }
//
//    @Before("matchAnnotation()")
//    public void checkC() {
//        System.out.println("通过注解进行认证");
//    }
}
