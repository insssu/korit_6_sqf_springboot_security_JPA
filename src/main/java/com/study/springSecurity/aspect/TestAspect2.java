package com.study.springSecurity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// aop 를 만들거면 아래 코드 그대로~ 사용하면 되고, class명만 바꿔주면 된다.
@Aspect
@Component
@Order(value = 1)   // value 의 수가 작을수록 실행순서가 높다
public class TestAspect2 {

    // 어느 지점을 잘라서 원하는 지점에 넣어 줄 것인가
    @Pointcut("@annotation(com.study.springSecurity.aspect.annotation.Test2Aop)") // aop*(.. 0개이상의 매개변수)
    private void pointCut() {}  // 함수명 정의
//    @Pointcut("@annotation(com.study.springSecurity.aspect.annotation.Test2Aop)")
//    private void pointCut2() {}  // 함수명 정의

    @Around("pointCut()")       // & 를 통해 여러개의 pointCut 을 사용 가능 잘 쓰지는 않음. 특정 구간에 annotation을 쓰는게 낫다.
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();  // getSignature() 은 안에있는 메서드들의 타입을 꺼내서 쓸 수 있다.
        System.out.println(signature.getName());                // 메소드명(aopTest2) 출력
        System.out.println(signature.getDeclaringTypeName());   // class명 출력
        Object[] args = proceedingJoinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        for (int i = 0; i < args.length; i++) {
            System.out.println(paramNames[i] + " : " + args[i]);
        }

        System.out.println("------------------------------------------------------");
//        for(Object obj : proceedingJoinPoint.getArgs()) {   // getArgs() 의 return이 배열이므로 반복을 돌릴 수 있다.
//            System.out.println(obj);
//        }
//        signature.getParameterNames(); // 메소드가 호출이 될 때 매개변수가 어떤게 넘어오냐에 따라 aop에 다른 조건을  줄 수 있음. 매개변수에 조건을 주고 동작을 설정할 수 있는 것. 분기점을 임의로 줄 수 있다.
//        signature.getParameterTypes();
        System.out.println("get메소드 호출 / 전처리2");
        Object result = proceedingJoinPoint.proceed();          // 핵심기능 호출
        System.out.println("get메소드 리턴되기 직전 / 후처리2");

        return result;
    }
}
