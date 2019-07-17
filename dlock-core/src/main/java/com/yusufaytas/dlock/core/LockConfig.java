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
