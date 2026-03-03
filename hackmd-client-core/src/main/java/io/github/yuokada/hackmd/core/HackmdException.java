package io.github.yuokada.hackmd.core;

/** Runtime exception thrown by HackmdClient implementations. */
public class HackmdException extends RuntimeException {

  private final int statusCode;

  public HackmdException(String message) {
    super(message);
    this.statusCode = -1;
  }

  public HackmdException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public HackmdException(String message, Throwable cause) {
    super(message, cause);
    this.statusCode = -1;
  }

  public HackmdException(int statusCode, String message, Throwable cause) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  /** Returns the HTTP status code associated with this exception, or -1 if unknown. */
  public int getStatusCode() {
    return statusCode;
  }
}
