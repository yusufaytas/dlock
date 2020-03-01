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

import com.yusufaytas.dlock.TryLock;

/**
 * A lock registrar finds TryLock annotations and decorates them with lock logic.
 */
public interface LockRegistrar {

  /**
   * Determines if the decorated method should be called. It'll be called if a lock implementation
   * isn't found or lock doesn't exist.
   *
   * @param tryLock
   * @return true if lock doesn't exist
   */
  boolean shouldProceed(TryLock tryLock);
}
