package com.study.springSecurity.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)     // 프로그램 실행 중에
@Target({ElementType.METHOD})           // 메소드 위에 줄 수 있는 어노테이션이다를 명시해주는 것
public @interface Test2Aop {}
