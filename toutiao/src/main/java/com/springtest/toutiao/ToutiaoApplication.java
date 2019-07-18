package com.springtest.toutiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 此类为入口类
 */
//Spring Boot项目的核心注解，主要目的是开启自动配置
//Spring Boot会自动扫描入口类的同级包及下级包中的Bean
    //关闭特定的自动配置使用{exclude = {DataSourceAutoConfiguration.class};
@SpringBootApplication
public class ToutiaoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ToutiaoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ToutiaoApplication.class);
    }

//    /**
//     * 配置一个Thymeleaf的ViewResolver，用来映射路径和实际页面的位置
//     * spring MVC 框架户
//     * ViewResolver是spring MVC视图渲染的核心机制
//     * @return
//     */
//    @Bean
//    public InternalResourceViewResolver viewResolver(){
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/src/main/resources");
//        viewResolver.setSuffix(".html");
//        viewResolver.setViewClass(ThymeleafView.class);
//        return viewResolver;
//    }

}

