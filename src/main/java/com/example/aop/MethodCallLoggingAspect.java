package com.example.aop;


import com.example.localization.Localization;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodCallLoggingAspect {

    private final Localization localization;

    @Autowired
    public MethodCallLoggingAspect(Localization localization) {
        this.localization = localization;
    }

    @Before("execution(* com.example.service.BookService.*(..))")
    public void logMethodCall(org.aspectj.lang.JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        System.out.println(localization.getMessage("aop.method") + " " + methodName);
    }

}
