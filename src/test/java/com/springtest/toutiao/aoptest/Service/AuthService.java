package com.springtest.toutiao.aoptest.Service;

import com.springtest.toutiao.aoptest.security.CurrentUserHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public void checkAccess() {
        String user = CurrentUserHolder.get();
        if(!"admin".equals(user)){
            throw new RuntimeException("operation not allow");
        }
    }
}
