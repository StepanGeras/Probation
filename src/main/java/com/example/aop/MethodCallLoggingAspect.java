package com.example.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodCallLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodCallLoggingAspect.class);

    @Before("execution(* com.example.service.BookService.*(..))")
    public void logMethodCall(org.aspectj.lang.JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        logger.info("Method called: {}" ,methodName);
    }

}
