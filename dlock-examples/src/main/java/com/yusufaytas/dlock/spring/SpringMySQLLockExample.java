package com.yusufaytas.dlock.spring;

import com.yusufaytas.dlock.TryLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
@ImportResource("classpath:mysql_lock.xml")
public class SpringMySQLLockExample {

  public static void main(String[] args) throws InterruptedException {
    System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "mysql");
    SpringApplication.run(SpringMySQLLockExample.class, args);
  }

  @Scheduled(cron = "*/1 * * * * *")
  @TryLock(name = "mysql", owner = "dlock", lockFor = 1)
  public void exampleLock() {
    System.out.println("lock works");
  }
}
