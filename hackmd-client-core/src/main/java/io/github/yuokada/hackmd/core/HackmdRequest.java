package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

/** Transport-level request model for HackmdTransport implementations. */
public record HackmdRequest(
    HackmdHttpMethod method,
    String path,
    Map<String, List<String>> headers,
    Map<String, List<String>> queryParameters,
    byte[] body) {

  public HackmdRequest {
    headers = headers == null ? Map.of() : Map.copyOf(headers);
    queryParameters = queryParameters == null ? Map.of() : Map.copyOf(queryParameters);
    body = body == null ? null : body.clone();
  }
}
