package com.yusufaytas.dlock.spring;

import com.yusufaytas.dlock.TryLock;
import com.yusufaytas.dlock.core.IntervalLock;
import com.yusufaytas.dlock.core.LockConfig;
import com.yusufaytas.dlock.core.UnreachableLockException;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("!test")
public class TryLockAspect {

  private final static Logger logger = LoggerFactory.getLogger(TryLockAspect.class);

  @Autowired
  private IntervalLock intervalLock;

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
      LockConfig config = new LockConfig(tryLock.name(), tryLock.owner(), tryLock.lockFor());
      return intervalLock.tryLock(config);
    } catch (UnreachableLockException e) {
      logger.error("Couldn't lock due to lock provider", e);
      return false;
    }
  }
}
