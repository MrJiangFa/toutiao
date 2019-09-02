package com.springtest.toutiao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import java.util.logging.Logger;

@Aspect
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.springtest.toutiao.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method:");
    }

    @After("execution(无法拦截动态生成的方法")
    public void afterMethod() {
        logger.info("after method:");
    }
}
