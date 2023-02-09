package com.example.demo.log;

import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class Monitoring {

    @Autowired
    AppLogger appLogger;

    @AfterThrowing(value = "(execution(* com.example.demo..*.*(..)) || execution(* com.example.demo..*.*(..)) || execution(* com.example.demo..*.*(..))) && args(input)", throwing = "throwable")
    public void logServiceException(JoinPoint joinPoint, Object input, Exception throwable) throws Throwable {

        appLogger.logException(joinPoint, input, throwable);
    }

    @Before(value = "(execution(* com.example.demo.course.controller..*.*(..))  && args(input))")
    public void logServiceCallInput(JoinPoint joinPoint, Object input) {
        appLogger.logRequestInput(joinPoint, input);
    }

    @AfterReturning(value = "execution(* com.example.demo.course.controller..*.*(..)) ", returning = "result")
    public void logServiceCallOutput(JoinPoint joinPoint, Object result) {
        appLogger.logRequestOutput(joinPoint, result);

    }

    @Before(value = "execution(* com.example.demo.controller..*.*(..)) && args(input))")
    public void logControllerCallInput(JoinPoint joinPoint, Object input) {
        appLogger.logRequestInput(joinPoint, input);
    }

//    @AfterReturning(value = "execution(* com.example.demo.controller..*.*(..))", returning = "result")
//    public void logControllerCallOutput(JoinPoint joinPoint, Object result) {
//        appLogger.logRequestOutput(joinPoint, result);
//        ThreadContext.pop();
//    }

    @AfterReturning(value = "execution(@org.springframework.web.bind.annotation.ExceptionHandler * *(..))", returning = "result")
    public void afterHandleError(JoinPoint joinPoint, Object result) {
        appLogger.logRequestOutput(joinPoint, result);
        ThreadContext.pop();
    }
}
