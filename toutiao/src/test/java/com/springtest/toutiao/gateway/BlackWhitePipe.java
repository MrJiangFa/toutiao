package com.springtest.toutiao.gateway;

public class BlackWhitePipe extends Pipe {
    @Override
    public void doPipe(HttpPipeInput input) {
        //黑白名单的处理逻辑
        System.out.println("过滤黑名单");
    }
}
