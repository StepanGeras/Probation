package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class MethodCachingAspect {

    private final Map<String, Object> cache = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MethodCachingAspect.class);

    @Around("execution(* com.example.service.BookService.*(..)) && !execution(* com.example.service.BookService.readBooks(..))")
    public Object cacheMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Object[] methodArgs = joinPoint.getArgs();

        String cacheKey = methodName + Arrays.toString(methodArgs);

        if (cache.containsKey(cacheKey)) {
            logger.info("Returning cached result for method: {}", methodName);
            return cache.get(cacheKey);
        }

        Object result = joinPoint.proceed();

        cache.put(cacheKey, result);

        return result;
    }

}
