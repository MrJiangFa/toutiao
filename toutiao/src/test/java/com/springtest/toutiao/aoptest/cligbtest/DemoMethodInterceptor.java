package com.springtest.toutiao.aoptest.cligbtest;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DemoMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before in cglib");
        Object res = null;
        try {
            res = methodProxy.invokeSuper(o, objects);
        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());
            throw e;
        } finally {
            System.out.println("after");
        }
        return res;
    }
}
