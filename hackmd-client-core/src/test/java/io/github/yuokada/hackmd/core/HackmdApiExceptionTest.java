package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HackmdApiExceptionTest {

  @Test
  @DisplayName("api exception exposes structured fields")
  void apiException_exposesStructuredFields() {
    var headers = Map.of("X-Request-Id", List.of("req-123"));
    var ex = new HackmdApiException(400, "bad request", "body", "E400", headers, false);

    assertEquals(400, ex.getStatusCode());
    assertEquals("body", ex.getResponseBodySnippet());
    assertEquals("E400", ex.getErrorCode());
    assertEquals(List.of("req-123"), ex.getResponseHeaders().get("X-Request-Id"));
    assertFalse(ex.isRetryable());
  }

  @Test
  @DisplayName("specialized exceptions keep type and retry semantics")
  void specializedExceptions_keepExpectedProperties() {
    var unauthorized =
        new HackmdUnauthorizedException(401, "unauthorized", null, null, Map.of(), false);
    var rateLimit = new HackmdRateLimitException("rate limited", null, null, Map.of(), true);
    var server = new HackmdServerException(503, "server error", null, null, Map.of(), true);

    assertEquals(401, unauthorized.getStatusCode());
    assertEquals(429, rateLimit.getStatusCode());
    assertEquals(503, server.getStatusCode());
    assertTrue(rateLimit.isRetryable());
    assertTrue(server.isRetryable());
  }
}
