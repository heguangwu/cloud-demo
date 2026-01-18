package cloud.heguangwu.order.aspect;

import cloud.heguangwu.order.annotation.MyLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OrderAopAspect {
    /**
     * 匹配特定包下的所有方法（service包及子包）
     * execution规则：(返回值类型  包名.类名.函数名(参数类型))
     * ".."表示任意目录(当前目录及子目录)，后面的"*"表示匹配所有，"(..)"表示匹配函数的任意参数
     */
    @Around("execution(* cloud.heguangwu.order.service..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("[execution-service] service method: {}", methodName);
        return joinPoint.proceed();
    }

    /***
     * 拦截方法上的注解，如 @MyLog
     */
    @Around("@annotation(cloud.heguangwu.order.annotation.MyLog)")
    public Object aroundMyLogAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("[@annotation] method with @MyLog: {}", methodName);
        return joinPoint.proceed();
    }

    /**
     * 截方法上的注解并获取注解的对象放到最后一个函数参数
     */
    @Around("@annotation(myLog)")
    public Object aroundMyLogAnnotationWithParams(ProceedingJoinPoint joinPoint, MyLog myLog) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("[@annotation] method with @MyLog: {}(desc={}, type={}, time={})", methodName,
                myLog.desc(), myLog.type(), myLog.timeField());
        return joinPoint.proceed();
    }

    /**
     * 拦截类上的注解，如 @RestController
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object aroundWithin(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("====> [@annotation] method with @Within: {}", methodName);
        return joinPoint.proceed();
    }
}
