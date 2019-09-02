package com.springtest.toutiao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;



@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
