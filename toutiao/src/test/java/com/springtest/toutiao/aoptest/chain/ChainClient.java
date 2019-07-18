package com.springtest.toutiao.aoptest.chain;

import java.util.Arrays;
import java.util.List;

public class ChainClient {
    static class ChainHandlerA extends ChainHandler {

        @Override
        protected void processHandler() {
            System.out.println("handle by handlerA");
        }
    }

    static class ChainHandlerB extends ChainHandler {

        @Override
        protected void processHandler() {
            System.out.println("handle by handlerB");
        }
    }

    static class ChainHandlerC extends ChainHandler {

        @Override
        protected void processHandler() {
            System.out.println("handle by handlerC");
        }
    }

    public static void main(String[] args) {
        List<ChainHandler> handlers = Arrays.asList(new ChainHandlerA(),new ChainHandlerB(),new ChainHandlerC());
        Chain chain = new Chain(handlers);
        chain.proceed();
    }
}
