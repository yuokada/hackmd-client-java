package io.github.yuokada.hackmd.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StaticTokenCredentialsProviderTest {

  @Test
  @DisplayName("always returns configured token")
  void returnsConfiguredToken() {
    var provider = new StaticTokenCredentialsProvider("test-token");
    var context = new HackmdRequestContext("op", "/v1/notes", HackmdHttpMethod.GET);
    assertEquals("test-token", provider.token(context));
  }
}
