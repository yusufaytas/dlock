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
package com.yusufaytas.dlock.spring;


import com.yusufaytas.dlock.TryLock;
import com.yusufaytas.dlock.core.IntervalLock;
import com.yusufaytas.dlock.core.LockConfig;
import com.yusufaytas.dlock.core.LockRegistry;
import com.yusufaytas.dlock.core.UnreachableLockException;
import java.lang.reflect.Method;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IntervalLockRegistrar {

  private final static Logger logger = LoggerFactory.getLogger(IntervalLockRegistrar.class);

  @Autowired(required = false)
  public IntervalLockRegistrar(IntervalLock intervalLock) {
    if(intervalLock == null){
      logger.warn("Couldn't find any IntervalLock bean to register.");
      return;
    }
    LockRegistry.setLock(intervalLock);
  }

  @Around("@annotation(com.yusufaytas.dlock.TryLock)")
  public void tryLock(ProceedingJoinPoint joinPoint)
      throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    TryLock tryLock = method.getAnnotation(TryLock.class);

    if (shouldProceed(tryLock)) {
      joinPoint.proceed();
    }
  }

  private boolean shouldProceed(TryLock tryLock) {
    try {
      Optional<IntervalLock> lock = LockRegistry.getLock();
      if (!lock.isPresent()) {
        return true;
      }
      LockConfig config = new LockConfig(tryLock.name(), tryLock.owner(), tryLock.lockFor());
      return lock.get().tryLock(config);
    } catch (UnreachableLockException e) {
      logger.error("Couldn't lock due to lock provider", e);
      return false;
    }
  }
}
