/**
 * Copyright © 2018 Yusuf Aytas. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
