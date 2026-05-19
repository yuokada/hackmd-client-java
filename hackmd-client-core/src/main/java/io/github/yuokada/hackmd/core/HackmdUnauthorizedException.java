package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

/** API exception for HTTP 401 or 403 authorization failures. */
public class HackmdUnauthorizedException extends HackmdApiException {

  public HackmdUnauthorizedException(
      int statusCode,
      String message,
      String responseBodySnippet,
      String errorCode,
      Map<String, List<String>> responseHeaders,
      boolean retryable) {
    super(statusCode, message, responseBodySnippet, errorCode, responseHeaders, retryable);
  }
}
