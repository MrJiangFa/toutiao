package com.springtest.toutiao.spring_event_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MyEventPublisher {
    @Autowired
    ApplicationContext applicationContext;

    //使用ApplicationContext publishEvent() 来发布事件
    public void publish(String msg) {
        applicationContext.publishEvent(new MyEvent(this, msg));
    }
}
