package com.springtest.toutiao.sensitivewords.event;

import java.util.EventListener;

public interface CacheChangedListener extends EventListener {
    public void handleChangedEvent(CacheChangedEvent event) throws Exception;

    public String getListenerName();
}
