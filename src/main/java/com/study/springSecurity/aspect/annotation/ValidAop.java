package com.study.springSecurity.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 적용시키는 시점
@Target({ElementType.METHOD, ElementType.TYPE}) // TYPE : 클래스 위에 쓸 수 있는 것
public @interface ValidAop {}
