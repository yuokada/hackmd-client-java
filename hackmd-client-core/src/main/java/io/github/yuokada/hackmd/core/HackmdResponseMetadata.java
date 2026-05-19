package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

/** Response metadata exposed by advanced API methods. */
public record HackmdResponseMetadata(
    Map<String, List<String>> headers,
    String apiVersion,
    String requestId,
    String rateLimitLimit,
    String rateLimitRemaining,
    String rateLimitReset) {

  public HackmdResponseMetadata {
    headers = headers == null ? Map.of() : Map.copyOf(headers);
  }
}
