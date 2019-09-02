package com.springtest.toutiao.aoptest.staticproxy;

public class Client {
    public static void main(String[] args){
        Subject subject = new Proxy(new RealSubject());
        subject.request();
    }
}
