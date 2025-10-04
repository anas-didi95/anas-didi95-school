/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.common.error.BaseError;
import com.anasdidi.school.common.error.E01ValidationError;
import com.anasdidi.school.common.error.E98VertxError;
import com.anasdidi.school.common.error.E99UnexpectedError;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Factory
@RequiredArgsConstructor
@Slf4j
class HttpExceptionHandlerConfig {

  private final LocalizedMessageSource messageSource;

  @Singleton
  @Requires(classes = {ConstraintViolationException.class})
  ExceptionHandler<ConstraintViolationException, HttpResponse<?>> E01ValidationError() {
    return (request, exception) -> {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      E01ValidationError error = new E01ValidationError(Map.of("message", exception.getMessage()));
      String message = getMessage(error, httpStatus);
      return prepareResponse("E01ValidationError", error, message, request, httpStatus);
    };
  }

  @Singleton
  @Requires(classes = {BaseError.class})
  ExceptionHandler<BaseError, HttpResponse<?>> BaseError() {
    return (request, error) -> {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      String message = getMessage(error, httpStatus);
      return prepareResponse(error.getClass().getSimpleName(), error, message, request, httpStatus);
    };
  }

  @Singleton
  @Requires(classes = {Exception.class, ExceptionHandler.class})
  ExceptionHandler<Exception, HttpResponse<?>> E99UnexpectedError() {
    return (request, exception) -> {
      if (exception instanceof ReplyException e) {
        var json = new JsonObject(e.getMessage());
        try {
          var error = CommonConstants.Error.valueOf(json.getString(BaseError.PARAM_ERROR));
          var variables = json.getJsonArray(BaseError.PARAM_VARIABLES);
          return BaseError().handle(request, new E98VertxError(error, variables));
        } catch (IllegalArgumentException e1) {
          log.error("Fail to get error code! {}", json.encode());
        }
      }

      log.error("", exception);

      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      E99UnexpectedError error = new E99UnexpectedError(Map.of("message", exception.getMessage()));
      String message = getMessage(error, httpStatus);
      return prepareResponse("E99UnexpectedError", error, message, request, httpStatus);
    };
  }

  private String getMessage(BaseError error, HttpStatus httpStatus) {
    String message =
        "[%s] %s"
            .formatted(
                error.error.code(),
                messageSource.getMessageOrDefault(
                    "error." + error.error.name(),
                    httpStatus.getReason(),
                    Optional.ofNullable(error.variables).orElse(new String[] {})));
    return Optional.ofNullable(MDC.get(CommonConstants.MDC_TRACEID))
        .map(s -> message + " | Ref[%s]".formatted(s))
        .orElse(message);
  }

  private HttpResponse<?> prepareResponse(
      String logTag,
      BaseError exception,
      String message,
      HttpRequest<?> request,
      HttpStatus httpStatus) {
    log.debug("[{}] errorCode={}, message={}", logTag, exception.error.code(), message);
    log.debug("[{}] variables={}", logTag, exception.variables);

    return HttpResponse.status(httpStatus).contentType(MediaType.TEXT_PLAIN).body(message);
  }
}
