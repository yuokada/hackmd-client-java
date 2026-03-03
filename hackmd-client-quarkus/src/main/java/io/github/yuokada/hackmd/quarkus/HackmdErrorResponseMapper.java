package io.github.yuokada.hackmd.quarkus;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import io.github.yuokada.hackmd.core.HackmdException;

/** Maps non-successful HackMD responses into {@link HackmdException}. */
public class HackmdErrorResponseMapper implements ResponseExceptionMapper<HackmdException> {

  @Override
  public HackmdException toThrowable(Response response) {
    var message =
        new StringBuilder("HackMD API request failed: status=").append(response.getStatus());

    if (response.hasEntity()) {
      try {
        var body = response.readEntity(String.class);
        if (body != null && !body.isBlank()) {
          message.append(", body=").append(body);
        }
      } catch (IllegalStateException ignored) {
        // Entity already consumed elsewhere; ignore body extraction.
      }
    }

    return new HackmdException(response.getStatus(), message.toString());
  }

  @Override
  public boolean handles(int status, MultivaluedMap<String, Object> headers) {
    return status >= 400;
  }
}
