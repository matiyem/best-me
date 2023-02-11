package com.example.demo.log;

import com.example.demo.repositoryMongo.component.SaveOperation;
import com.example.demo.repositoryMongo.entity.Log;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppLogger {

    private Gson gSon;

    private Logger rootLogger = LogManager.getRootLogger();
    private Logger ReqLogger = LogManager.getLogger("ReqLogger");
    private Logger ExceptionLogger = LogManager.getLogger("ExcLogger");
    private Logger BusinessErrorLogger = LogManager.getLogger("BuzErrorLogger");

    @Autowired
    private SaveOperation saveOperation;

//    {
//        GsonBuilder builder = new GsonBuilder();
//        builder.setExclusionStrategies( new HiddenAnnotationExclusionStrategy());
//        builder.serializeNulls();
//        gSon = builder.create();
//    }

    public void logException(JoinPoint joinPoint, Object input, Throwable e) {
        ExceptionLogger.error(joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " input param : " + input.getClass().getSimpleName() + " : " + gSon.toJson(joinPoint.getArgs()) + " - Exception : " + e.getCause());
        ExceptionLogger.error(joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " input param : " + input.getClass().getSimpleName() + " : " + gSon.toJson(joinPoint.getArgs()) + " - ExceptionStackTrace : " + e.getMessage());
        ExceptionLogger.error(joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " input param : " + input.getClass().getSimpleName() + " : " + gSon.toJson(joinPoint.getArgs()) + " - ExceptionStackTrace : " + e);
        e.printStackTrace();
    }

    public void logRequestInput(JoinPoint joinPoint, Object input) {

//        ReqLogger.info(joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " executing - input param : " + input.getClass().getSimpleName() + " " + gSon.toJson(joinPoint.getArgs()));

    }
    public void logRequestOutput(JoinPoint joinPoint, Object result) {
        saveOperation.saveInMongo(new Log(joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " executed - output param : " + result.getClass().getSimpleName()));
        ReqLogger.info(joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " executed - output param : " + result.getClass().getSimpleName());
//        ReqLogger.info(joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName() + " executed - output param : " + result.getClass().getSimpleName() + " " + gSon.toJson(new Object()));
    }




}
