package com.springtest.toutiao.aoptest.chain;

public abstract class ChainHandler {
    public void execute(Chain chain) {
        processHandler();
        chain.proceed();
    }

    protected abstract void processHandler();
}
