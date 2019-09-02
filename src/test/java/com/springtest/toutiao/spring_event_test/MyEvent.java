package com.springtest.toutiao.spring_event_test;

import org.springframework.context.ApplicationEvent;

/**
 * Spring 中的 ApplicationEvent 维持了一个系统当前时间的时间戳
 */
public class MyEvent extends ApplicationEvent {
    private String msg;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

}
