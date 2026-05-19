package io.github.yuokada.hackmd.core;

import java.util.Locale;

/** HTTP methods supported by HackmdTransport requests. */
public enum HackmdHttpMethod {
  GET,
  POST,
  PUT,
  PATCH,
  DELETE;

  /**
   * Parses HTTP method name safely.
   *
   * <p>Unknown or unsupported methods are mapped to {@link #GET} as a conservative default for
   * request-context metadata.
   */
  public static HackmdHttpMethod fromString(String methodName) {
    if (methodName == null || methodName.isBlank()) {
      return GET;
    }
    try {
      return valueOf(methodName.toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException ex) {
      return GET;
    }
  }
}
