package io.github.yuokada.hackmd.core;

import java.util.List;
import java.util.Map;

/** Transport-level response model for HackmdTransport implementations. */
public record HackmdResponse(int statusCode, Map<String, List<String>> headers, byte[] body) {

  public HackmdResponse {
    headers = headers == null ? Map.of() : Map.copyOf(headers);
    body = body == null ? null : body.clone();
  }
}
