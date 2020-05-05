package com.elite.webdata.jwt;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtIgnore {

}
