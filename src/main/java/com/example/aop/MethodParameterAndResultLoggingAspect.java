package com.example.aop;

import com.example.localization.Localization;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MethodParameterAndResultLoggingAspect {

    private final Localization localization;

    @Autowired
    public MethodParameterAndResultLoggingAspect(Localization localization) {
        this.localization = localization;
    }

    @Around("execution(* com.example.service.BookService.*(..))")
    public Object logMethodParametersAndResults(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Object[] methodArgs = joinPoint.getArgs();

        System.out.println(localization.getMessage("aop.method.arg") + " " + methodName + ": " + Arrays.toString(methodArgs));

        Object result = joinPoint.proceed();

        System.out.println(localization.getMessage("aop.method.result") + " " + methodName + ": " + result);

        return result;
    }

}
