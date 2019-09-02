package com.springtest.toutiao.beantest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InjectionService {
    @Autowired
    InjectionDAO injectionDAO;

    public void save(String arg) {
        arg = arg + ":" + this.hashCode();
        injectionDAO.save(arg);
    }
}
