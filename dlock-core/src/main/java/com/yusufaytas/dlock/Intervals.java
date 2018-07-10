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

/**
 * Useful common intervals for the lock
 */
public class Intervals {

  public static final long ONE_MINUTE = 60;
  public static final long FIVE_MINUTE = 5 * ONE_MINUTE;
  public static final long TEN_MINUTE = 10 * ONE_MINUTE;
  public static final long ONE_HOUR = 60 * ONE_MINUTE;
  public static final long TWO_HOUR = 2 * ONE_HOUR;
  public static final long THREE_HOUR = 3 * ONE_HOUR;
  public static final long DAY = 24 * ONE_HOUR;
}
