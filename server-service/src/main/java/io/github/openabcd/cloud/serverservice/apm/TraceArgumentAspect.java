package io.github.openabcd.cloud.serverservice.apm;

import io.github.openabcd.cloud.common.JsonUtil;
import io.opentelemetry.api.trace.Span;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraceArgumentAspect {

    @Around("@annotation(io.github.openabcd.cloud.serverservice.apm.TraceArgument)")
    public Object logArguments(ProceedingJoinPoint joinPoint) throws Throwable {
        val span = Span.current();

        val method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        val methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();

        val paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        val args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            val name = paramNames[i];
            val json = JsonUtil.toJson(args[i]);

            span.setAttribute(methodName + "." + name, json);
        }

        return joinPoint.proceed();
    }
}
