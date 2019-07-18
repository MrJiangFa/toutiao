package com.springtest.toutiao.gateway;

public class PipeTask implements Runnable{

    PipeTask(Pipe pipe){

    }
    @Override
    public void run() {
        //获取管道链
        System.out.println("执行管道链中的对应的功能模块：如校验、黑白名单验证等功能");
    }
}
