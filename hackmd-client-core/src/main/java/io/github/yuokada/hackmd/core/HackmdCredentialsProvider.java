package io.github.yuokada.hackmd.core;

/** Provider abstraction for API token resolution. */
@FunctionalInterface
public interface HackmdCredentialsProvider {

  String token(HackmdRequestContext context);
}
