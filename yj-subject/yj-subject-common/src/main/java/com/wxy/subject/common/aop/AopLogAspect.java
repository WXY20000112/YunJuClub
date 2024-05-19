package com.wxy.subject.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: YunJuClub-Flex
 * @description: Aop 日志切面
 * @author: 32115
 * @create: 2024-05-19 10:29
 */
@Aspect
@Component
public class AopLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AopLogAspect.class);

    /**
     * @author: 32115
     * @description: 配置切点方法
     * @date: 2024/5/19
     * @return: void
     */
    @Pointcut("@annotation(com.wxy.subject.common.aop.AopLogAnnotations)")
    public void aopLogCut(){}

    /**
     * @author: 32115
     * @description: 前置通知，在方法执行前执行
     * @date: 2024/5/19
     * @param: joinPoint
     * @return: void
     */
    @Before("aopLogCut()")
    public void before(JoinPoint joinPoint){
        logger.info("前置通知，在方法执行前执行");
        logger.info("{}.{}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    /**
     * @author: 32115
     * @description: 返回通知 方法返回后执行
     * @date: 2024/5/19
     * @param: joinPoint
     * @param: result 方法返回的结果
     * @return: void
     */
    @AfterReturning(value = "aopLogCut()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result){
        logger.info("方法返回通知");
        logger.info("{}.{}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    /**
     * @author: 32115
     * @description: 方法抛出异常后执行
     * @date: 2024/5/19
     * @param: joinPoint
     * @param: e
     * @return: void
     */
    @AfterThrowing(value = "aopLogCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e){
        logger.info("异常通知");
        logger.info("{}.{}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getMessage());
    }
}
