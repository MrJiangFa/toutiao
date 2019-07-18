package com.springtest.toutiao.gateway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpPipeInput {
    public HttpServletRequest request;
    public HttpServletResponse response;
    public HttpPipeInput(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }
}
