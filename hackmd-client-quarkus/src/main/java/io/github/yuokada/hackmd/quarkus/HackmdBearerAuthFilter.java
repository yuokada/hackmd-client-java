package io.github.yuokada.hackmd.quarkus;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

import io.github.yuokada.hackmd.core.HackmdCredentialsProvider;
import io.github.yuokada.hackmd.core.HackmdHttpMethod;
import io.github.yuokada.hackmd.core.HackmdRequestContext;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class HackmdBearerAuthFilter implements ClientRequestFilter {

  private final HackmdClientConfig config;
  private final HackmdCredentialsProvider credentialsProvider;

  @Inject
  public HackmdBearerAuthFilter(
      HackmdClientConfig config, HackmdCredentialsProvider credentialsProvider) {
    this.config = config;
    this.credentialsProvider = credentialsProvider;
  }

  @Override
  public void filter(ClientRequestContext requestContext) {
    var method = HackmdHttpMethod.fromString(requestContext.getMethod());
    var context = new HackmdRequestContext(
        "quarkus-rest-client-call", requestContext.getUri().getPath(), method);
    var token = credentialsProvider.token(context);
    requestContext.getHeaders().putSingle("Authorization", "Bearer " + token);
    requestContext.getHeaders().putSingle("User-Agent", config.userAgent());
  }
}
