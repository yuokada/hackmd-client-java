package io.github.yuokada.hackmd.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HackmdHttpMethodTest {

  @Test
  @DisplayName("fromString parses case-insensitive known methods")
  void fromString_parsesKnownMethods() {
    assertEquals(HackmdHttpMethod.GET, HackmdHttpMethod.fromString("GET"));
    assertEquals(HackmdHttpMethod.POST, HackmdHttpMethod.fromString("post"));
    assertEquals(HackmdHttpMethod.PATCH, HackmdHttpMethod.fromString("PaTcH"));
  }

  @Test
  @DisplayName("fromString falls back to GET for unknown or null methods")
  void fromString_fallbacksToGet() {
    assertEquals(HackmdHttpMethod.GET, HackmdHttpMethod.fromString("HEAD"));
    assertEquals(HackmdHttpMethod.GET, HackmdHttpMethod.fromString("OPTIONS"));
    assertEquals(HackmdHttpMethod.GET, HackmdHttpMethod.fromString(null));
    assertEquals(HackmdHttpMethod.GET, HackmdHttpMethod.fromString(""));
  }
}
