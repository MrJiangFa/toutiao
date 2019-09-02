package com.springtest.toutiao.aoptest.chain;

public class Client {
    static class HandlerA extends Handler {

        @Override
        protected void handlerProcess() {
            System.out.println("handle by handlerA");
        }
    }

    static class HandlerB extends Handler {

        @Override
        protected void handlerProcess() {
            System.out.println("handle by handlerB");
        }
    }

    static class HandlerC extends Handler {

        @Override
        protected void handlerProcess() {
            System.out.println("handle by handlerC");
        }
    }

    public static void main(String[] args) {
        Handler handlerA = new HandlerA();
        Handler handlerB = new HandlerB();
        Handler handlerC = new HandlerC();
        handlerA.setSuccessor(handlerB);
        handlerB.setSuccessor(handlerC);
        handlerA.execute();
    }

}
