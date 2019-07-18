package com.springtest.toutiao.aoptest.cligbtest;

import com.springtest.toutiao.aoptest.staticproxy.RealSubject;
import com.springtest.toutiao.aoptest.staticproxy.Subject;
import org.springframework.cglib.proxy.Enhancer;

public class Client {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback(new DemoMethodInterceptor());
        Subject subject = (Subject) enhancer.create();
        subject.hello();
    }
}
