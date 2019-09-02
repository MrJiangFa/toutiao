package com.springtest.toutiao.aoptest.security;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AdminOnly {

}
