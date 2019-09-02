package com.springtest.toutiao.spring_event_test;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<MyEvent> {
    //处理发布事件带来的消息；
    @Override
    public void onApplicationEvent(MyEvent event) {
        String msg = event.getMsg();
        System.out.println("MyListener 接收到了 MyEvent 发布的消息：" + msg);
    }
}
