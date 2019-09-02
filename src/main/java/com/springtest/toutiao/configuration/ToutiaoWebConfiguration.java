package com.springtest.toutiao.configuration;

import com.springtest.toutiao.interceptor.LoginRequiredInterceptor;
import com.springtest.toutiao.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ToutiaoWebConfiguration implements WebMvcConfigurer {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);//全局
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting*");//只处理对应路径的页面
    }
}
