package com.springtest.toutiao.aoptest.chain;

public abstract class Handler {

    private Handler successor;

    public Handler getSuccessor(){
        return successor;
    }

    public void setSuccessor(Handler successor){
        this.successor = successor;
    }

    public void execute(){
        handlerProcess();
        if(successor!=null){
            successor.execute();
        }
    }

    protected abstract void handlerProcess();
}
