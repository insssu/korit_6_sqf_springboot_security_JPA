package com.study.springSecurity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// aop 를 만들거면 아래 코드 그대로~ 사용하면 되고, class명만 바꿔주면 된다.
@Aspect
@Component
@Order(value = 2 )
public class TestAspect {

    // 어느 지점을 잘라서 원하는 지점에 넣어 줄 것인가
    @Pointcut("execution(* com.study.springSecurity.service.*.aop*(..))") // aop*(.. 0개이상의 매개변수)
    private void pointCut() {}  // 함수명 정의

    @Around("pointCut()")       // 위에서 정의한 함수 호출, 해당 pointcut() 위치에 아래 코드를 넣어줄 것이다. filter의 느낌
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("get메소드 호출 / 전처리");
        Object result = proceedingJoinPoint.proceed();          // proceddingJoinPoint : 핵심기능 호출
        System.out.println("get메소드 리턴되기 직전 / 후처리");

        return result;
    }
}
