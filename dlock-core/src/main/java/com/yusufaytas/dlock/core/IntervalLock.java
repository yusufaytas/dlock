/**
 * Copyright © 2019 Yusuf Aytas. All rights reserved.
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