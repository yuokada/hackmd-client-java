package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import java.util.Map;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import io.github.yuokada.hackmd.core.HackmdApiException;
import io.github.yuokada.hackmd.core.HackmdException;
import io.github.yuokada.hackmd.core.HackmdRateLimitException;
import io.github.yuokada.hackmd.core.HackmdServerException;
import io.github.yuokada.hackmd.core.HackmdUnauthorizedException;

/** Maps non-successful HackMD responses into {@link HackmdException}. */
public class HackmdErrorResponseMapper implements ResponseExceptionMapper<HackmdException> {

  @Override
  public HackmdException toThrowable(Response response) {
    var status = response.getStatus();
    var responseHeaders = toHeaderMap(response.getHeaders());
    var bodySnippet = extractBody(response);
    var message = new StringBuilder("HackMD API request failed: status=").append(status);
    if (bodySnippet != null && !bodySnippet.isBlank()) {
      message.append(", body=").append(bodySnippet);
    }

    var retryable = status == 429 || status >= 500;
    String errorCode = null;

    if (status == 401 || status == 403) {
      return new HackmdUnauthorizedException(
          status, message.toString(), bodySnippet, errorCode, responseHeaders, false);
    }
    if (status == 429) {
      return new HackmdRateLimitException(
          message.toString(), bodySnippet, errorCode, responseHeaders, true);
    }
    if (status >= 500) {
      return new HackmdServerException(
          status, message.toString(), bodySnippet, errorCode, responseHeaders, true);
    }

    return new HackmdApiException(
        status, message.toString(), bodySnippet, errorCode, responseHeaders, retryable);
  }

  @Override
  public boolean handles(int status, MultivaluedMap<String, Object> headers) {
    return status >= 400;
  }

  private static String extractBody(Response response) {
    if (!response.hasEntity()) {
      return null;
    }
    try {
      var body = response.readEntity(String.class);
      if (body == null) {
        return null;
      }
      return body.length() <= 512 ? body : body.substring(0, 512);
    } catch (IllegalStateException ignored) {
      // Entity already consumed elsewhere; ignore body extraction.
      return null;
    }
  }

  private static Map<String, List<String>> toHeaderMap(MultivaluedMap<String, Object> source) {
    if (source == null || source.isEmpty()) {
      return Map.of();
    }
    var headers = new java.util.HashMap<String, List<String>>();
    for (var entry : source.entrySet()) {
      var values = entry.getValue().stream().map(String::valueOf).toList();
      headers.put(entry.getKey(), values);
    }
    return Map.copyOf(headers);
  }
}
