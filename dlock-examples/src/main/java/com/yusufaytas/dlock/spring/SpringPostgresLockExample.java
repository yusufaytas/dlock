package com.yusufaytas.dlock.spring;

import static com.yusufaytas.dlock.Intervals.ONE_MINUTE;

import com.yusufaytas.dlock.TryLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
@ImportResource("classpath:postgres_lock.xml")
public class SpringPostgresLockExample {

  public static void main(String[] args) throws InterruptedException {
    System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "postgres");
    SpringApplication.run(SpringPostgresLockExample.class, args);
  }

  @Scheduled(cron = "*/1 * * * * *")
  @TryLock(name = "postgres", owner = "dlock", lockFor = ONE_MINUTE)
  public void exampleLock() {
    System.out.println("lock works");
  }
}
