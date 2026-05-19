package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

/** Exception for HTTP/API-level failures returned by HackMD. */
public class HackmdApiException extends HackmdException {

  private final String responseBodySnippet;
  private final String errorCode;
  private final Map<String, List<String>> responseHeaders;
  private final boolean retryable;

  public HackmdApiException(
      int statusCode,
      String message,
      String responseBodySnippet,
      String errorCode,
      Map<String, List<String>> responseHeaders,
      boolean retryable) {
    super(statusCode, message);
    this.responseBodySnippet = responseBodySnippet;
    this.errorCode = errorCode;
    this.responseHeaders = responseHeaders == null ? Map.of() : Map.copyOf(responseHeaders);
    this.retryable = retryable;
  }

  public String getResponseBodySnippet() {
    return responseBodySnippet;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public Map<String, List<String>> getResponseHeaders() {
    return responseHeaders;
  }

  public boolean isRetryable() {
    return retryable;
  }
}
