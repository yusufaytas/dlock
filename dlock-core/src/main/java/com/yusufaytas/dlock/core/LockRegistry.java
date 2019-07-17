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
    return Optional.ofNullable(LOCK_REFERENCE.get());
  }
}
