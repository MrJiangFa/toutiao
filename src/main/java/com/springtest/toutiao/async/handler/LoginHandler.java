package com.springtest.toutiao.async.handler;

import com.springtest.toutiao.async.EventHandler;
import com.springtest.toutiao.async.EventModel;
import com.springtest.toutiao.async.EventType;

import java.util.Arrays;
import java.util.List;

public class LoginHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {

    }

    @Override
    public List<EventType> getSupprotEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
