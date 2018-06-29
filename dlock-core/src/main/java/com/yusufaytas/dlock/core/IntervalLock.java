package com.yusufaytas.dlock.core;

/**
 * IntervalLock acquires a lock for an interval and holds the lock until the interval expires.
 */
public interface IntervalLock {

  /**
   * Returns true if it's able to acquire the lock specified by {@param lockConfig}
   * @param lockConfig
   * @return
   */
  boolean tryLock(LockConfig lockConfig) throws UnreachableLockException;

}