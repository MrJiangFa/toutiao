package com.springtest.toutiao.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel model);//要处理这个EventModel
    List<EventType> getSupprotEventTypes();//要关注这些Event
}
