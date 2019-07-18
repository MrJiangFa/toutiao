package com.springtest.toutiao.beantest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class Main {
    public static void main(String[] args){
        XmlWebApplicationContext context1 = new XmlWebApplicationContext();
        ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InjectionConfiguration.class);
        InjectionService service = context.getBean(InjectionService.class);
        service.save("hello");
        context.close();
    }
}
