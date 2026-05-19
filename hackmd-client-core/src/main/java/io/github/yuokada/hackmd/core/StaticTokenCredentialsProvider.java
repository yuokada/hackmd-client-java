package io.github.yuokada.hackmd.core;

import java.util.Objects;

/** Fixed token credential provider for single-token applications. */
public class StaticTokenCredentialsProvider implements HackmdCredentialsProvider {

  private final String token;

  public StaticTokenCredentialsProvider(String token) {
    this.token = Objects.requireNonNull(token, "token must not be null");
  }

  @Override
  public String token(HackmdRequestContext context) {
    return token;
  }
}
