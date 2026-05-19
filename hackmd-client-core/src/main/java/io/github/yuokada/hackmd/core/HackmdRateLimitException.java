package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

/** API exception for HTTP 429 (rate limited). */
public class HackmdRateLimitException extends HackmdApiException {

  public HackmdRateLimitException(
      String message,
      String responseBodySnippet,
      String errorCode,
      Map<String, List<String>> responseHeaders,
      boolean retryable) {
    super(429, message, responseBodySnippet, errorCode, responseHeaders, retryable);
  }
}
