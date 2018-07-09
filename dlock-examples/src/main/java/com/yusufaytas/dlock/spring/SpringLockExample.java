package com.yusufaytas.dlock.spring;

import static com.yusufaytas.dlock.Intervals.ONE_MINUTE;

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
  @TryLock(name = "example", owner = "dlock", lockFor = ONE_MINUTE)
  public void exampleLock() {
    System.out.println("lock works");
  }
}
