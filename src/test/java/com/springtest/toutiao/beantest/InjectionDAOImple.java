package com.springtest.toutiao.beantest;

import org.springframework.stereotype.Service;

@Service
public class InjectionDAOImple implements InjectionDAO{
    @Override
    public void save(String arg){
        System.out.println("保存数据"+arg);
    }
}
