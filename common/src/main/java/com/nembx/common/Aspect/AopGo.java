package com.nembx.common.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Lian
 */

@Aspect
@Slf4j
@Component
public class AopGo {
    @Pointcut("@annotation(LogAop)")
    public void annotation(){

    }
    @Around("annotation()")
    public Object aroundLogin(ProceedingJoinPoint pjp){
        log.info("开始记录日志");
        Object proceed = null;
        try {
            proceed = pjp.proceed();
            long time = System.currentTimeMillis();
            log.info(pjp.getSignature().getName() + "方法执行时间：" + (System.currentTimeMillis() - time) + "ms");
            log.info("{}",proceed);
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("出现错误");
        }
        log.info("记录结束");
        return proceed;
    }
}
