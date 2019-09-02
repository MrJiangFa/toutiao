package com.springtest.toutiao.aoptest.Service;

import com.springtest.toutiao.aoptest.domain.Product;
import com.springtest.toutiao.aoptest.security.AdminOnly;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @AdminOnly
    public void insert(Product product) {
        System.out.println("insert product successfully");
    }

    @AdminOnly
    public void delete(int id) {
        System.out.println("delete product successfully");
    }
}
