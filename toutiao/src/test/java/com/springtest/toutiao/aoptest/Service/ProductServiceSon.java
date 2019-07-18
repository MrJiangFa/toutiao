package com.springtest.toutiao.aoptest.Service;

import com.springtest.toutiao.aoptest.security.AdminOnly;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceSon extends ProductService {
    @AdminOnly
    public String append() {
        System.out.println("add new product!");
        return "productserviceson";
    }
}
