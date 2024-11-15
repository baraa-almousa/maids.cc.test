package com.java.springBoot.app.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // تعريف النقطة المشتركة لجميع طرق إضافة الكتاب
    @Pointcut("execution(* com.java.springBoot.app.Service.BookService.addBook(..))")
    public void addBookPointcut() {}

    // تعريف النقطة المشتركة لجميع طرق تحديث الكتاب
    @Pointcut("execution(* com.java.springBoot.app.Service.BookService.updateBook(..))")
    public void updateBookPointcut() {}

    // تعريف النقطة المشتركة لجميع طرق عمليات استعارة الكتاب
    @Pointcut("execution(* com.java.springBoot.app.Service.BorrowingRecordService.borrowBook(..))")
    public void borrowBookPointcut() {}

    // تسجيل المكالمات إلى طريقة addBook
    @Before("addBookPointcut()")
    public void logBeforeAddBook(JoinPoint joinPoint) {
        logger.info("Calling method: {}", joinPoint.getSignature().getName());
    }

    // تسجيل المكالمات إلى طريقة updateBook
    @Before("updateBookPointcut()")
    public void logBeforeUpdateBook(JoinPoint joinPoint) {
        logger.info("Calling method: {}", joinPoint.getSignature().getName());
    }

    // تسجيل المكالمات إلى طريقة borrowBook
    @Before("borrowBookPointcut()")
    public void logBeforeBorrowBook(JoinPoint joinPoint) {
        logger.info("Calling method: {}", joinPoint.getSignature().getName());
    }

    // تسجيل الانتهاء من العملية ووقت التنفيذ
    @After("execution(* com.java.springBoot.app.Service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method {} executed", joinPoint.getSignature().getName());
    }

    // تسجيل الاستثناءات عند حدوثها
    @AfterThrowing(pointcut = "execution(* com.java.springBoot.app.Service.*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        logger.error("Method {} threw exception: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }

    // حساب الأداء
    @Around("execution(* com.java.springBoot.app.Service.BookService.addBook(..))")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }
}
