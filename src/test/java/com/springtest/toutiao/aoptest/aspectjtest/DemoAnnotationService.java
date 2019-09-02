package com.springtest.toutiao.aoptest.aspectjtest;

import org.springframework.stereotype.Service;

/**
 * 使用注解的被拦截类
 */
@Service
public class DemoAnnotationService {
    @Action(name="注解式拦截的add操作")
    public void add(){}

}
