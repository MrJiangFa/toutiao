package com.springtest.toutiao.aoptest.dynamic;

import com.springtest.toutiao.aoptest.staticproxy.RealSubject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 相当于aop的aspect
 */
public class JDKProxySubject implements InvocationHandler {

    RealSubject realSubject;

    public JDKProxySubject(RealSubject realSubject){
        this.realSubject = realSubject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object res = null;
        try{
            res = method.invoke(realSubject,args);
        }catch (Exception e){
            System.out.println("exception:"+e.getMessage());
            throw e;
        }finally {
            System.out.println("after");
        }
        return res;
    }
}
