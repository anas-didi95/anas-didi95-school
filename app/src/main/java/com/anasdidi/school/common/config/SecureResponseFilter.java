/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.ResponseFilter;
import io.micronaut.http.annotation.ServerFilter;
import jakarta.annotation.Nullable;
import java.util.Optional;
import lombok.AllArgsConstructor;

@ServerFilter(ServerFilter.MATCH_ALL_PATTERN)
@AllArgsConstructor
class SecureResponseFilter {

  private final TraceContext traceContext;

  @ResponseFilter
  void responseFilter(MutableHttpResponse<?> response, @Nullable Throwable failure) {
    // === Caching & Transport Security ===
    response.getHeaders().add("Cache-Control", "no-store, no-cache, must-revalidate");
    response.getHeaders().add("Pragma", "no-cache");
    response
        .getHeaders()
        .add("Strict-Transport-Security", "max-age=63072000; includeSubDomains; preload");

    // === Security Headers ===
    response.getHeaders().add("X-Content-Type-Options", "nosniff");
    response.getHeaders().add("X-Frame-Options", "DENY");
    response.getHeaders().add("X-Download-Options", "noopen");
    response.getHeaders().add("Referrer-Policy", "strict-origin-when-cross-origin");
    response.getHeaders().add("Permissions-Policy", "geolocation=(), microphone=(), camera=()");
    // response.getHeaders().add("Content-Security-Policy", "default-src 'none'; frame-ancestors
    // 'none'"); // Disable to enable swagger-ui
    response.getHeaders().add("Cross-Origin-Resource-Policy", "same-origin");
    response.getHeaders().add("Cross-Origin-Opener-Policy", "same-origin");
    response.getHeaders().add("Cross-Origin-Embedder-Policy", "require-corp");

    // === CORS (Flexible for both public/private use) ===
    // response.getHeaders().add("Access-Control-Allow-Origin", "https://your-frontend-domain.com");
    // response.getHeaders().add("Access-Control-Allow-Credentials", "true");
    // response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    // response.getHeaders().add("Access-Control-Allow-Headers", "Authorization, Content-Type,
    // X-Requested-With");

    // === Custom ===
    Optional.ofNullable(traceContext.getTraceId())
        .ifPresent(v -> response.getHeaders().add("X-Trace-Id", v));
  }
}
