package com.study.springSecurity.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TimePrintAspect {

    @Pointcut("@annotation(com.study.springSecurity.aspect.annotation.TimeAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        String infoPrint = "ClassName(" + signature.getDeclaringType().getSimpleName() + ") MethodName(" + signature.getName() + ")";
        String linePrint = "";
        for (int i = 0; i < infoPrint.length(); i++) {
            linePrint += "-";
        }
        log.info("{}", linePrint);
        log.info("{}", infoPrint);
        log.info("TotalTime : {}초", stopWatch.getTotalTimeSeconds());
        log.info("{}", linePrint);

        return result;       // 매개변수가 잘 들어왔는지 확인하는 메서드. 따라서 전처리에만 사용하므로 후처리는 필요없으니 리턴에 바로 들어가도 된다.
    }
}
