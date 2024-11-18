package com.nembx.common.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;

/**
 * @author Lian
 */

@Slf4j
@Component
@Aspect
public class AopRun {

    @Pointcut("@annotation(RunTimeRecord)")
    public void anno(){

    }

    @Before("anno()")
    public void runTimeBefore(JoinPoint joinPoint){
        log.info("方法：{}",joinPoint.getSignature().getName());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RunTimeRecord annotation = AnnotationUtils.findAnnotation(signature.getMethod(), RunTimeRecord.class); // 获取注解
        log.info("注解：{}",annotation.value());
        MDC.put("startTime", String.valueOf(System.currentTimeMillis()));
    }

    @After("anno()")
    public void runTimeAfter(JoinPoint joinPoint){
        long endTime = System.currentTimeMillis();
        log.info("方法：{}，耗时：{}ms",joinPoint.getSignature().getName(),
                endTime - Long.parseLong(MDC.get("startTime")));
        MDC.clear();
    }
}


