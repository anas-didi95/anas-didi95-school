/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import com.anasdidi.school.common.CommonConstants;
import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;
import java.security.Principal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

@InterceptorBean(TraceLog.class)
@AllArgsConstructor
@Slf4j
class TraceLogInterceptor implements MethodInterceptor<Object, Object> {

  private static final SecureRandom RANDOM = new SecureRandom();
  private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

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

    if (StringUtils.isBlank(MDC.get(CommonConstants.MDC_TRACEID))) {
      byte[] buffer = new byte[6];
      RANDOM.nextBytes(buffer);
      MDC.put(
          CommonConstants.MDC_TRACEID,
          FORMATTER.format(LocalDateTime.now()) + ENCODER.encodeToString(buffer));
    }
    MDC.put(CommonConstants.MDC_CLASSMETHOD, classMethod);

    long timeStart = System.currentTimeMillis();
    log.info("REQ: {}", parameters);
    Object result = context.proceed();
    log.info("RES: {}", Optional.ofNullable(result).orElse("No result"));
    log.info("END: timeTaken={}ms", System.currentTimeMillis() - timeStart);

    return result;
  }
}
