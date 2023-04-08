package hello.board.support.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogAdvice {
//
//    @Pointcut("execution(* hello.board.domain.forbiddenword.service.ForbiddenWordCache.*.*(..))")
//    private void cut(){}
//
//    @Around("cut()")
//    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
//
//        String params = getRequestParams();
//
//        long startAt = System.currentTimeMillis();
//
//        logger.info("----------> REQUEST : {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),
//                pjp.getSignature().getName(), params);
//
//        Object result = pjp.proceed();
//
//        long endAt = System.currentTimeMillis();
//
//        logger.info("----------> RESPONSE : {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
//                pjp.getSignature().getName(), result, endAt-startAt);
//
//        return result;
//
//    }
}
