package com.springtest.toutiao.servlettest;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;

public class AsyncRequestProcessor implements Runnable {
    private AsyncContext asyncContext;//异步上下文
    private int time;//模拟业务逻辑执行的时间

    AsyncRequestProcessor() {
    }

    AsyncRequestProcessor(AsyncContext asyncContext, int time) {
        this.asyncContext = asyncContext;
        this.time = time;
    }

    @Override
    public void run() {
        System.out.println("是否异步：" + asyncContext.getRequest().isAsyncSupported());
        longProcessing(time);
        try {
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.write("处理完成" + time + "ms!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟业务逻辑处理的时间
     * @param time
     */
    public void longProcessing(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
