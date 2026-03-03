package io.github.yuokada.hackmd.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class HackmdExceptionTest {

  @Test
  @DisplayName("message-only constructor sets statusCode to -1")
  void messageOnly_setsStatusCodeToMinusOne() {
    var ex = new HackmdException("not found");
    assertEquals("not found", ex.getMessage());
    assertEquals(-1, ex.getStatusCode());
  }

  @Test
  @DisplayName("statusCode+message constructor stores both values")
  void statusCodeAndMessage_storesBothValues() {
    var ex = new HackmdException(404, "not found");
    assertEquals(404, ex.getStatusCode());
    assertEquals("not found", ex.getMessage());
  }

  @Test
  @DisplayName("message+cause constructor sets statusCode to -1 and preserves cause")
  void messageAndCause_setsStatusCodeToMinusOne() {
    var cause = new RuntimeException("root cause");
    var ex = new HackmdException("wrapped", cause);
    assertEquals(-1, ex.getStatusCode());
    assertEquals("wrapped", ex.getMessage());
    assertSame(cause, ex.getCause());
  }

  @Test
  @DisplayName("statusCode+message+cause constructor stores all three values")
  void statusCodeMessageAndCause_storesAllValues() {
    var cause = new RuntimeException("root cause");
    var ex = new HackmdException(500, "server error", cause);
    assertEquals(500, ex.getStatusCode());
    assertEquals("server error", ex.getMessage());
    assertSame(cause, ex.getCause());
  }
}
