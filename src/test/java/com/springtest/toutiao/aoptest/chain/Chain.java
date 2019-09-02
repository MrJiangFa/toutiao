package com.springtest.toutiao.aoptest.chain;

import java.util.List;

public class Chain {
    public Chain(List<ChainHandler> handlers) {
        this.handlers = handlers;
    }

    private List<ChainHandler> handlers;

    private int index = 0;

    public void proceed() {
        if (index >= handlers.size()) {
            return;
        }
        handlers.get(index++).execute(this);
    }
}
