package com.yusufaytas.dlock.core;

/**
 * Configuration for distributed locks.
 */
public class LockConfig {

  /**
   * The name of the lock
   */
  private String name;
  /**
   * The owner of the lock
   */
  private String owner;
  /**
   * The duration to hold the lock
   */
  private long duration;

  public LockConfig(String name, String owner, long duration) {
    this.name = name;
    this.owner = owner;
    this.duration = duration;
  }

  public String getName() {
    return name;
  }

  public String getOwner() {
    return owner;
  }

  public long getDuration() {
    return duration;
  }
}
