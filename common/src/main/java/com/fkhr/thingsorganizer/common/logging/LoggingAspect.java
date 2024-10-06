package com.fkhr.thingsorganizer.common.logging;

import com.fkhr.thingsorganizer.common.util.InfoProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
    @Autowired
    private InfoProperties infoProperties;
    @Pointcut("execution(* com.fkhr.thingsorganizer.*.service.*.save(..))")
    private void saveMethods(){
    }

    @Pointcut("within(com.fkhr.thingsorganizer.*.controller..*)")
    private void apiCalls(){}

    @Before("apiCalls()")
    public void logApiRequest(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();

       if(infoProperties.isVisible()){
        System.out.println(infoProperties.toString());
       }
        logger.debug("Is calling API " + methodName + " with arguments: ");
    }

    @Before("saveMethods()")
    public void logSaveMethods(){
        System.out.println("Is saving...");
    }

}
