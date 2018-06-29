package com.yusufaytas.dlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method with TryLock annotation would only be executed if and only if the lock is acquired. If
 * the lock isn't acquired than the method won't be called. Moreover, the method won't be executed
 * anywhere until the duration for the lock expires.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TryLock {

  /**
   * Returns the name of the lock
   */
  String name();

  /**
   * Returns the owner of the lock
   */
  String owner() default "";

  /**
   * Returns the duration for the lock in millis
   */
  long lockFor();
}
