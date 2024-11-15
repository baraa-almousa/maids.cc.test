package com.java.springBoot.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
    @Aspect
    public class LoggingAspect {

        @Before("execution(* com.java.springBoot.app.Service.*.*(..))")
        public void logMethodCalls() {
            System.out.println("Logging method call...");
        }
    }
}
