package io.github.yuokada.hackmd.core;

/** Runtime exception thrown by HackmdClient implementations. */
public class HackmdException extends RuntimeException {

  public HackmdException(String message) {
    super(message);
  }

  public HackmdException(String message, Throwable cause) {
    super(message, cause);
  }
}
