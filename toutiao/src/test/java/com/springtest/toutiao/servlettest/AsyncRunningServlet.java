package com.springtest.toutiao.servlettest;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

@WebServlet(urlPatterns = "/asyncRunning", asyncSupported = true)
public class AsyncRunningServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        System.out.println("serlvet 初始化");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        System.out.println("AsyncRunningServlet Start | Name" + Thread.currentThread().getName() +
                "| ID:" + Thread.currentThread().getId());
        request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        //动态设置模拟后续逻辑业务处理的时间，便于测试对比异步上下文超时与不超时的代码反应
        String time = request.getParameter("time");
        int processTime = Integer.valueOf(time);
        if (processTime > 5000) {
            processTime = 5000;
        }
        AsyncContext ac = request.startAsync();
        ac.addListener(new MyAsyncListener());//添加异步监听器
        ac.setTimeout(4000);//异步处理对应的超时时间
        ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext().getAttribute("executor");
        executor.execute(new AsyncRequestProcessor(ac, processTime));
        long endTime = System.currentTimeMillis();
        System.out.println("AsyncRunningServlet End | Name=" + Thread.currentThread().getName() +
                " | ID=" + Thread.currentThread().getId() + "| Time Cost=" + (endTime - startTime) + "ms");

    }
}
