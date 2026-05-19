package io.github.yuokada.hackmd.quarkus;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import io.github.yuokada.hackmd.core.HackmdApiException;
import io.github.yuokada.hackmd.core.HackmdException;

public class HackmdRetryExecutor {

  private final HackmdClientConfig config;

  public HackmdRetryExecutor(HackmdClientConfig config) {
    this.config = config;
  }

  public <T> T run(boolean retryableOperation, Supplier<T> operation) {
    if (!config.retryEnabled() || !retryableOperation) {
      return operation.get();
    }
    var maxRetries = Math.max(0, config.retryMaxRetries());
    var delayMillis = Math.max(1L, config.retryDelayMillis());

    int attempt = 0;
    while (true) {
      try {
        return operation.get();
      } catch (HackmdApiException ex) {
        if (!ex.isRetryable() || attempt >= maxRetries) {
          throw ex;
        }
        sleep(resolveDelayMillis(delayMillis, attempt, ex.getResponseHeaders()));
      } catch (HackmdException ex) {
        throw ex;
      } catch (RuntimeException ex) {
        if (attempt >= maxRetries) {
          throw ex;
        }
        sleep(applyJitter(delayMillis << Math.min(attempt, 8)));
      }
      attempt++;
    }
  }

  private static long resolveDelayMillis(
      long baseDelayMillis, int attempt, java.util.Map<String, List<String>> responseHeaders) {
    var retryAfter = findHeader(responseHeaders, "Retry-After");
    if (retryAfter != null) {
      try {
        return Math.max(1L, Long.parseLong(retryAfter) * 1000L);
      } catch (NumberFormatException ignored) {
        // Ignore non-numeric Retry-After for now.
      }
    }
    return applyJitter(baseDelayMillis << Math.min(attempt, 8));
  }

  private static String findHeader(java.util.Map<String, List<String>> headers, String name) {
    if (headers == null || headers.isEmpty()) {
      return null;
    }
    return headers.entrySet().stream()
        .filter(e -> e.getKey() != null && e.getKey().equalsIgnoreCase(name))
        .findFirst()
        .map(java.util.Map.Entry::getValue)
        .filter(values -> values != null && !values.isEmpty())
        .map(values -> values.get(0))
        .orElse(null);
  }

  private static long applyJitter(long millis) {
    var jitter = ThreadLocalRandom.current().nextLong(0, Math.max(2L, millis / 4));
    return millis + jitter;
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Retry sleep interrupted", e);
    }
  }
}
