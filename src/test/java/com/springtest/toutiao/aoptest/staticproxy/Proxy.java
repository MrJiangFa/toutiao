package com.springtest.toutiao.aoptest.staticproxy;

public class Proxy implements Subject {
    private RealSubject realSubject;

    public Proxy(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void request() {
        System.out.println("before");
        try {
            realSubject.request();
        } catch (Exception e) {
            System.out.println("exception:" + e.getMessage());
        } finally {
            System.out.println("after");
        }
    }

    @Override
    public void hello(){
        realSubject.hello();
    }
}
