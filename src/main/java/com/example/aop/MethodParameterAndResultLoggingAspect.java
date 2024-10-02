package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MethodParameterAndResultLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodParameterAndResultLoggingAspect.class);

    @Around("execution(* com.example.service.BookService.*(..))")
    public Object logMethodParametersAndResults(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Object[] methodArgs = joinPoint.getArgs();

        logger.info("Method parameters for {}: {}", methodName, Arrays.toString(methodArgs));

        Object result = joinPoint.proceed();

        logger.info("Method result for {}: {}", methodName, result);

        return result;
    }

}
