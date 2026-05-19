package io.github.yuokada.hackmd.core;

/** Exception for client-side failures before/without a valid API response. */
public class HackmdClientException extends HackmdException {

  public HackmdClientException(String message) {
    super(message);
  }

  public HackmdClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
