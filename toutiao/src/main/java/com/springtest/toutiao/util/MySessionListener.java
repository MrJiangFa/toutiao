package com.springtest.toutiao.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * web.xml可以注册多个servlet事件监听器，
 * web容器按照其在web.xml中的注册顺序来加载这些servlet事件监听器
 * servlet事件监听器的注册和调用过程都是由web容器自动完成的
 * 一个web应用程序只会为每个事件监听器创建一个实例对象，需要注意线程安全性
 * <p>
 * 按监听的对象，监听器可以分为三类：
 * 1. 监听应用程序上下文(ServletContext)
 * 2. 监听用户会话（HttpSession）
 * 3. 监听请求消息（ServletRequest）
 * <p>
 * ServletContext、HttpSession、ServletRequest三个类的对象对应的作用域：
 * 1. ServletContext对应于容器的产生和销毁
 * 2. HttpSession对应于当个用户的登录和注销
 * 3. ServletRequest对应于一个http请求
 * <p>
 * 三类监听器分别对应ServletContextListener、HttpSessionListener、ServletRequestListener三个接口、
 * 1. ServletContextListener定义了两个事件处理方法：contextInitialized(ServletContextEvent sce)和contextDestroyed(ServletContextEvent sce)
 * 可以通过ServletContextEvent参数获取当前被创建的ServletContext对象
 * 2. HttpSessionListener接口中定义了两个事件处理方法：sessionCreated(HttpSessonEvent se)和sessionDestroyed(HttpSessionEvent se)
 * 3. ServletRequestListener接口定义了两个时间处理方法：requestInitialized(ServletRequestEvent sre)和requestDestroyed(ServletRequestEvent sre)
 * <p>
 * 按监听的事件类划分：
 * 1. 监听域“对象自身”的创建和销毁
 * 2. 监听域对象中的“属性”的增加和删除的事件监听器
 * 3. 监听“绑定到HttpSession域”中的某个对象的状态的事件监听器
 * <p>
 * web容器负责创建和销毁ServletContext、HttpSession、ServletRequest
 */
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
