package io.github.yuokada.hackmd.quarkus;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "hackmd")
public interface HackmdClientConfig {

  /** Bearer token (API token). */
  String token();

  /** Optional User-Agent for your application/library. */
  @WithDefault("hackmd-client-quarkus")
  String userAgent();
}
