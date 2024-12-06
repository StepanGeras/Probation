package com.example.aop;

import com.example.localization.Localization;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class MethodCachingAspect {

    private final Localization localization;
    private final Map<String, Object> cache = new HashMap<>();

    @Autowired
    public MethodCachingAspect(Localization localization) {
        this.localization = localization;
    }

    @Around("execution(* com.example.service.BookService.*(..)) && !execution(* com.example.service.BookService.readBooks(..))")
    public Object cacheMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Object[] methodArgs = joinPoint.getArgs();

        String cacheKey = methodName + Arrays.toString(methodArgs);

        if (cache.containsKey(cacheKey)) {
            System.out.println(localization.getMessage("aop.method.cache.return.result") + " " + methodName);
            return cache.get(cacheKey);
        }

        Object result = joinPoint.proceed();

        cache.put(cacheKey, result);

        return result;
    }

}
