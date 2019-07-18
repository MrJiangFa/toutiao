package com.springtest.toutiao.aoptest.dynamic;

import com.springtest.toutiao.aoptest.staticproxy.RealSubject;
import com.springtest.toutiao.aoptest.staticproxy.Subject;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args){
        Subject subject = (Subject)Proxy.newProxyInstance(Client.class.getClassLoader(),new Class[]{Subject.class},
                new JDKProxySubject(new RealSubject()));
        subject.hello();
    }
}
