/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@InterceptorBean(TraceLog.class)
@AllArgsConstructor
@Slf4j
class TraceLogInterceptor implements MethodInterceptor<Object, Object> {

  private final TraceContext traceContext;

  @Override
  public @Nullable Object intercept(MethodInvocationContext<Object, Object> context) {
    String classMethod =
        "%s.%s".formatted(context.getDeclaringType().getSimpleName(), context.getMethodName());
    String parameters =
        context.getParameterValueMap().size() > 0
            ? String.join(
                ", ",
                context.getParameterValueMap().entrySet().stream()
                    .map(
                        o -> {
                          if (o.getValue() == null) {
                            return "%s=null".formatted(o.getKey());
                          }
                          String value =
                              switch (o.getValue()) {
                                case Principal o2 -> o2.getName();
                                default -> o.getValue().toString();
                              };
                          return "%s=%s".formatted(o.getKey(), value);
                        })
                    .toList())
            : "No parameter";

    boolean isController = context.hasAnnotation(Controller.class);
    if (isController) {
      traceContext.setController(classMethod);
      traceContext.setControllerParam(parameters);
    }

    traceContext.setClassMethod(classMethod);
    if (Objects.isNull(traceContext.getTraceId())) {
      traceContext.setTraceId(UUID.randomUUID());
      MDC.put("traceId", traceContext.getTraceId().toString());
    }

    long timeStart = System.currentTimeMillis();
    log.info("[{}] REQ: {}", classMethod, parameters);
    Object result = context.proceed();
    log.info("[{}] RES: {}", classMethod, Optional.ofNullable(result).orElse("No result"));
    log.info("[{}] END: timeTaken={}ms", classMethod, System.currentTimeMillis() - timeStart);

    return result;
  }
}
