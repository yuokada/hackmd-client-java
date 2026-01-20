package io.github.yuokada.hackmd.quarkus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.github.yuokada.hackmd.core.HackmdClient;

@ApplicationScoped
public class HackmdClientProducer {

  private final HackmdRestClient restClient;

  @Inject
  public HackmdClientProducer(@RestClient HackmdRestClient restClient) {
    this.restClient = restClient;
  }

  @Produces
  @ApplicationScoped
  public HackmdClient hackmdClient() {
    return new HackmdClientImpl(restClient);
  }
}
