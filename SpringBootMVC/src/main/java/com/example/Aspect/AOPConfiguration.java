package com.example.Aspect;

import com.example.SpringBootMvcApplication;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by magicliang on 2016/3/19.
 */
@Aspect
@Component
public class AOPConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SpringBootMvcApplication.class);

    public AOPConfiguration(){
        log.info("AOP beginning!!!!!!!!!!!!!!!!!!!!!!!");
    }

    //
    //@Pointcut("execution(* ab*(..))")
    @Pointcut("execution(public String abc(..))")
    public void executeABC() {
        //this method will never be invoked.
        System.out.println("pointcut abc");
    }

    @Around("executeABC()")
    //@Before("execution(public String executeABC())")
    private Object aroundMethod(ProceedingJoinPoint thisJoinPoint) {
        System.out.println("切面abc执行了。。。。");
        try {
            String s = (String) thisJoinPoint.proceed();
            System.out.println("response is: " + s);
            return s;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    @AfterReturning("execution(* com..HelloController.*(..))")
    private void afterControllerMethodReturning(JoinPoint joinPoint) {
        System.out.println("@AfterReturning: " + joinPoint);
    }

    @AfterReturning("execution(* com.example.controller..*.*(..))")
    private void anyPublicOperation() {
        System.out.println("public operation ");
     }
    //@Before("com.example.controller.HelloController.abc()")
    //public void BeforeABC() {
        //System.out.println("before abc");
    //}

}
