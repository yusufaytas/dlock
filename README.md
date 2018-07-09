DLock
========
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt) [![Build Status](https://travis-ci.org/yusufaytas/dlock.png?branch=master)](https://travis-ci.org/yusufaytas/dlock)

DLock is a library to get a distributed interval lock. An Interval Lock is a lock where the acquirer acquires the lock for a certain amount of time. Once the lock is acquired, it won't be released till the interval ends.

Feedback and pull-requests are welcome!
+ [Usage](#usage)
+ [Lock Implementations](#lock-implementations)
  - [Postgres](#postgres)
+ [Contributing](#contributing)

# Usage
## Requirements and dependencies
* Java 8
* slf4j-api

Note that, there should be only one global lock.

## Import project
#### maven
```xml
<dependency>
    <groupId>com.yusufaytas.dlock</groupId>
    <artifactId>dlock-spring</artifactId>
    <version>0.1.0</version>
</dependency>
<dependency>
    <groupId>com.yusufaytas.dlock</groupId>
    <artifactId>dlock-jdbc</artifactId>
    <version>0.1.0</version>
</dependency>
```
#### gradle
```groovy
compile 'com.yusufaytas.dlock:dlock-spring:0.1.0'
compile 'com.yusufaytas.dlock:dlock-jdbc:0.1.0'
```
## Add a Interval Lock Support
#### spring bean config
An example lock support for Postgres can be added as follows
```xml
<bean id="postgresLock" class="com.yusufaytas.dlock.jdbc.PostgresIntervalLock">
  <constructor-arg type="javax.sql.DataSource" ref="lockDataSource"/>
</bean>
```
#### Java Code
```java
@Scheduled(cron = "*/1 * * * * *")
@TryLock(name = "example", owner = "dlock", lockFor = 10)
public void exampleLock() {
  System.out.println("lock works");
}
```
# Lock Implementations
## Postgres
We insert into postgres if there doesn't exist a lock. 
