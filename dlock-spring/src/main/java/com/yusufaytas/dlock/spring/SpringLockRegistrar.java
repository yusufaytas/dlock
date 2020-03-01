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
package com.yusufaytas.dlock.spring;


import static com.yusufaytas.dlock.core.LockRegistry.setLock;

import com.yusufaytas.dlock.TryLock;
import com.yusufaytas.dlock.core.AbstractLockRegistrar;
import com.yusufaytas.dlock.core.IntervalLock;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpringLockRegistrar extends AbstractLockRegistrar {

  private final static Logger logger = LoggerFactory.getLogger(SpringLockRegistrar.class);
  private final static String LOCK_OWNER_PROPERTY_NAME = "com.yusufaytas.dlock.owner";

  @Value("${" + LOCK_OWNER_PROPERTY_NAME + "}")
  private String ownerFromProperty;

  @Autowired(required = false)
  public SpringLockRegistrar(IntervalLock intervalLock) {
    if (intervalLock == null) {
      logger.warn("Couldn't find any IntervalLock bean to register.");
      return;
    }
    setLock(intervalLock);
  }

  @Around("@annotation(com.yusufaytas.dlock.TryLock)")
  public void tryLock(ProceedingJoinPoint joinPoint)
      throws Throwable {
    final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    final Method method = signature.getMethod();
    final TryLock tryLock = method.getAnnotation(TryLock.class);

    if (shouldProceed(tryLock)) {
      joinPoint.proceed();
    }
  }

  @Override
  protected String getOwner(final TryLock tryLock) {
    if (tryLock.owner().isEmpty() && !ownerFromProperty.equals(LOCK_OWNER_PROPERTY_NAME)) {
      return ownerFromProperty;
    }
    return super.getOwner(tryLock);
  }

}
