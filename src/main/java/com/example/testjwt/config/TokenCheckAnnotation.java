package com.example.testjwt.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 是否需要验证注解
 *
 * @author zwq
 * @date 2021/9/8 15:01
 */
@Target(ElementType.METHOD) // 修饰范围
@Retention(RetentionPolicy.RUNTIME) // 用来描述注解的声明周期
public @interface TokenCheckAnnotation {
}
