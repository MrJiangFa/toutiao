package com.springtest.toutiao.servlettest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Servlet上下文监听器，可以在里面初始化业务线程池
 */
public class AppContextListener implements ServletContextListener {
    /**
     * 初始化业务工作线程池；
     *
     * @param event
     */
    public void contextInitialized(ServletContextEvent event) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 5000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        event.getServletContext().setAttribute("executor",executor);
    }
    public void contextDestroyed(ServletContextEvent event){
        ThreadPoolExecutor executor = (ThreadPoolExecutor)event.getServletContext().getAttribute("executor");
        executor.shutdown();
    }
}
