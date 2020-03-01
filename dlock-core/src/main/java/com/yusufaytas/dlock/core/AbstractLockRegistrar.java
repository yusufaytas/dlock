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

import static com.yusufaytas.dlock.core.LockRegistry.getLock;

import com.yusufaytas.dlock.TryLock;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base implementation for lock registrar.
 */
public class AbstractLockRegistrar {

  private final static Logger logger = LoggerFactory.getLogger(AbstractLockRegistrar.class);
  private final Map<TryLock, LockConfig> locks = new ConcurrentHashMap<>();

  protected boolean shouldProceed(final TryLock tryLock) {
    try {
      final Optional<IntervalLock> lock = getLock();
      if (!lock.isPresent()) {
        return true;
      }
      final LockConfig config = locks.computeIfAbsent(tryLock, (t) -> getLockConfigWithDefaults(t));
      return lock.get().tryLock(config);
    } catch (UnreachableLockException e) {
      logger.error("Couldn't lock due to lock provider", e);
      return false;
    }
  }

  protected String getOwner(final TryLock tryLock) {
    if (tryLock.owner().isEmpty()) {
      return getHostname();
    }
    final String envOwner = System.getenv(tryLock.owner());
    if (envOwner == null || envOwner.isEmpty()) {
      return tryLock.owner();
    }
    return envOwner;
  }

  private LockConfig getLockConfigWithDefaults(final TryLock tryLock) {
    final String owner = getOwner(tryLock);
    return new LockConfig(tryLock.name(), owner, tryLock.lockFor());
  }

  private String getHostname() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      logger.warn("Couldn't get the hostname through InetAddress.", e);
      return System.getenv("HOSTNAME");
    }
  }
}
