package io.github.yuokada.hackmd.quarkus;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import jakarta.ws.rs.ext.Provider;

import io.github.yuokada.hackmd.core.HackmdResponseMetadata;

@Provider
public class HackmdResponseMetadataFilter implements ClientResponseFilter {

  // NOTE: This relies on request/response processing on the same thread.
  // Current usage is for blocking client flows.
  private static final ThreadLocal<HackmdResponseMetadata> LAST_METADATA = new ThreadLocal<>();

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext)
      throws IOException {
    var headers = toHeaderMap(responseContext.getHeaders());
    LAST_METADATA.set(new HackmdResponseMetadata(
        headers,
        firstHeader(headers, "X-API-Version"),
        firstHeader(headers, "X-Request-Id"),
        firstHeader(headers, "X-RateLimit-Limit"),
        firstHeader(headers, "X-RateLimit-Remaining"),
        firstHeader(headers, "X-RateLimit-Reset")));
  }

  public static HackmdResponseMetadata consume() {
    var metadata = LAST_METADATA.get();
    LAST_METADATA.remove();
    return metadata == null
        ? new HackmdResponseMetadata(Map.of(), null, null, null, null, null)
        : metadata;
  }

  private static Map<String, List<String>> toHeaderMap(Map<String, List<String>> headers) {
    if (headers == null || headers.isEmpty()) {
      return Map.of();
    }
    return Map.copyOf(headers);
  }

  private static String firstHeader(Map<String, List<String>> headers, String name) {
    return headers.entrySet().stream()
        .filter(e -> e.getKey() != null && e.getKey().equalsIgnoreCase(name))
        .findFirst()
        .map(Map.Entry::getValue)
        .filter(values -> values != null && !values.isEmpty())
        .map(values -> values.get(0))
        .orElse(null);
  }
}
