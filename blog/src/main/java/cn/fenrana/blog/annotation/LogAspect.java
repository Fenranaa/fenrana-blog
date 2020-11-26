package cn.fenrana.blog.annotation;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    //切点
    @Pointcut("@annotation(cn.fenrana.blog.annotation.OperateLog)")
    public void pointCut() {
    }


    @Before("pointCut()")
    public void aa(JoinPoint joinPoint) {
        log.info("============前置执行===============");
        log.info("请求的参数: " + Arrays.toString(joinPoint.getArgs()));
        log.info("响应方法:" + joinPoint.getSignature().getDeclaringTypeName());
        log.info("============前置完成===============");
    }

    @Around("pointCut()")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        (MethodSignature)proceedingJoinPoint.getSignature();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        OperateLog annotation = signature.getMethod().getAnnotation(OperateLog.class);

        Object proceed = proceedingJoinPoint.proceed();

        boolean saveRequestData = annotation.isSaveRequestData();
        String operateType = annotation.operateType().value();
        String operator = annotation.operatorType();


        proceedingJoinPoint.getArgs();

        return proceed;
    }

}
