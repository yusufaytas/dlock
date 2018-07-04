package com.yusufaytas.dlock.core;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A class to store lock.
 */
public class LockRegistry {

  private final static AtomicReference<IntervalLock> LOCK_REFERENCE = new AtomicReference<>();

  public static Optional<IntervalLock> setLock(IntervalLock lock) {
    if (lock == null) {
      throw new NullPointerException("Interval lock is required.");
    }
    LOCK_REFERENCE.set(lock);
    return Optional.of(lock);
  }

  public static Optional<IntervalLock> getLock() {
    return Optional.of(LOCK_REFERENCE.get());
  }
}
