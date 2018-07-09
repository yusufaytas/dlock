package com.yusufaytas.dlock.core;

/**
 * IntervalLock acquires a lock for an interval and holds the lock until the interval expires.
 */
public interface IntervalLock {

  /**
   * Returns true if it's able to acquire the lock specified by lockConfig
   * @param lockConfig config for the lock
   * @return
   */
  boolean tryLock(LockConfig lockConfig) throws UnreachableLockException;

}