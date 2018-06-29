package com.yusufaytas.dlock.spring;

import com.yusufaytas.dlock.TryLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
@ImportResource("classpath:lock.xml")
public class SpringLockExample {

  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(SpringLockExample.class, args);
  }

  @Scheduled(cron = "*/1 * * * * *")
  @TryLock(name = "something", lockFor = 1234)
  public void ultimateLock() {
    System.out.println("lock works");
  }

}
