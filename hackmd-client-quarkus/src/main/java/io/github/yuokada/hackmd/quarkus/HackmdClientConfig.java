package io.github.yuokada.hackmd.quarkus;

import jakarta.validation.constraints.NotBlank;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "hackmd")
public interface HackmdClientConfig {

  /** Bearer token (API token). Must not be blank. */
  @NotBlank(message = "hackmd.token must not be blank")
  String token();

  /** Optional User-Agent for your application/library. */
  @WithDefault("hackmd-client-quarkus")
  String userAgent();

  /** SDK-level retry switch (disabled by default). */
  @WithDefault("false")
  boolean retryEnabled();

  /** Max retry count when retry is enabled. */
  @WithDefault("3")
  int retryMaxRetries();

  /** Base delay in milliseconds for exponential backoff. */
  @WithDefault("500")
  long retryDelayMillis();
}
