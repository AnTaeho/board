package hello.board.support.aop;

import hello.board.support.annotation.CreateTransactional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogAdvice {

    @Around("@annotation(createTransactional)")
    public Object logging(ProceedingJoinPoint joinPoint, CreateTransactional createTransactional) throws Throwable {
        log.info("[create log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

}
