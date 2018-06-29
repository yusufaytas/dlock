package com.yusufaytas.dlock.core;

public class UnreachableLockException extends RuntimeException {

  public UnreachableLockException(String message) {
    super(message);
  }

  public UnreachableLockException(String message, Throwable cause) {
    super(message, cause);
  }
}
