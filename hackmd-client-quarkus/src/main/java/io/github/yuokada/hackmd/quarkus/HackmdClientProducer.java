package io.github.yuokada.hackmd.quarkus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.github.yuokada.hackmd.core.HackmdClient;
import io.github.yuokada.hackmd.core.HackmdCredentialsProvider;
import io.github.yuokada.hackmd.core.StaticTokenCredentialsProvider;

@ApplicationScoped
public class HackmdClientProducer {

  private final HackmdRestClient restClient;
  private final HackmdClientConfig config;

  @Inject
  public HackmdClientProducer(@RestClient HackmdRestClient restClient, HackmdClientConfig config) {
    this.restClient = restClient;
    this.config = config;
  }

  @Produces
  @ApplicationScoped
  public HackmdClient hackmdClient() {
    return new HackmdClientImpl(restClient);
  }

  @Produces
  @ApplicationScoped
  public HackmdCredentialsProvider hackmdCredentialsProvider() {
    return new StaticTokenCredentialsProvider(config.token());
  }
}
