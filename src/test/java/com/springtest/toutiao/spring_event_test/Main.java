package com.springtest.toutiao.spring_event_test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        MyEventPublisher publisher = context.getBean(MyEventPublisher.class);
        publisher.publish("publish application event");
        context.close();
    }
}
