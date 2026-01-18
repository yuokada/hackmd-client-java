package io.github.yuokada.hackmd.quarkus;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class HackmdBearerAuthFilter implements ClientRequestFilter {

  private final HackmdClientConfig config;

  @Inject
  public HackmdBearerAuthFilter(HackmdClientConfig config) {
    this.config = config;
  }

  @Override
  public void filter(ClientRequestContext requestContext) {
    requestContext.getHeaders().putSingle("Authorization", "Bearer " + config.token());
    requestContext.getHeaders().putSingle("User-Agent", config.userAgent());
  }
}
