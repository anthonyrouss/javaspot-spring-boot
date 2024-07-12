package gr.unipi.javaspot.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * gr.unipi.javaspot.services.impl.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(public * gr.unipi.javaspot.security.CustomUserDetailsService.loadUserByUsername(..))")
    public void userDetailsServiceMethod() {
    }

    @AfterThrowing(pointcut = "serviceMethods() || userDetailsServiceMethod()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) throws Throwable {
        String methodName = joinPoint.getSignature().toLongString();
        log.error("Exception thrown in method: {}, exception message: {}", methodName, ex.getMessage());
        throw ex;
    }

}
