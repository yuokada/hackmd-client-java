package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

/** API exception for HTTP 5xx errors. */
public class HackmdServerException extends HackmdApiException {

  public HackmdServerException(
      int statusCode,
      String message,
      String responseBodySnippet,
      String errorCode,
      Map<String, List<String>> responseHeaders,
      boolean retryable) {
    super(statusCode, message, responseBodySnippet, errorCode, responseHeaders, retryable);
  }
}
