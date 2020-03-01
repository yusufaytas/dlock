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
    System.out.println("postgres lock works");
  }

  @Scheduled(cron = "*/1 * * * * *")
  @TryLock(name = "springPropertyLock", lockFor = ONE_MINUTE)
  public void springPropertyLock() {
    System.out.println("spring property lock works");
  }
}
