DLock
========
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt) [![Build Status](https://travis-ci.org/yusufaytas/dlock.png?branch=master)](https://travis-ci.org/yusufaytas/dlock)

DLock is a library to get an interval lock. An Interval Lock is a lock where the acquirer acquires the lock for a certain amount of time. Once the lock is acquired, it won't be released till the interval ends. Note that, each of the Interval Lock implementations guarantees atomicity over processes and threads. The lock can only be acquired by only one process or thread at a time as expected. The implementations differ based on the technology.

Feedback and pull-requests are welcome!
+ [Usage](#usage)
+ [Lock Implementations](#lock-implementations)
  - [Postgres](#postgres)
  - [MySQL](#mysql)
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
    <groupId>com.yusufaytas</groupId>
    <artifactId>dlock-spring</artifactId>
    <version>0.2.1</version>
</dependency>
<dependency>
    <groupId>com.yusufaytas</groupId>
    <artifactId>dlock-jdbc</artifactId>
    <version>0.2.1</version>
</dependency>
```
or you can import all
```xml
<dependency>
    <groupId>com.yusufaytas</groupId>
    <artifactId>dlock-all</artifactId>
    <version>0.2.1</version>
</dependency>
```
#### gradle
```groovy
compile 'com.yusufaytas:dlock-spring:0.2.1'
compile 'com.yusufaytas:dlock-jdbc:0.2.1'
```
or you can import all
```groovy
compile 'com.yusufaytas:dlock-all:0.2.1'
```
## Add an Interval Lock Support
#### Spring Bean Config
An example lock support for Postgres can be added as follows
```xml
<!-- A bean for the lock implementation. Note that there should be only one global implementation-->
<bean id="postgresLock" class="com.yusufaytas.dlock.jdbc.PostgresIntervalLock">
  <constructor-arg type="javax.sql.DataSource" ref="lockDataSource"/>
</bean>
<!-- The lock gets auto-registered to the registrar -->
<bean id="lockRegistrar" class="com.yusufaytas.dlock.spring.SpringLockRegistrar"/>
```
#### Java Code
```java
@Scheduled(cron = "*/1 * * * * *")
@TryLock(name = "example", owner = "dlock", lockFor = ONE_MINUTE)
public void exampleLock() {
  System.out.println("lock works");
}
```
# Lock Implementations
## Jdbc
You need to execute the DDL at the target database with appropriate permissions to make the lock work.
### Postgres
We insert into postgres if there doesn't exist a lock. Please checkout the [Postgres DDL](https://github.com/yusufaytas/dlock/blob/master/dlock-jdbc/src/main/resources/ddls/postgres.ddl).
### MySQL
We get an exclusive lock on the lock table and insert a new lock if a conflicting lock doesn't exit. Please checkout the [MySQL DDL](https://github.com/yusufaytas/dlock/blob/master/dlock-jdbc/src/main/resources/ddls/mysql.ddl).
# Contributing
## Compiling project
```shell script
./mvnw clean install -Dgpg.skip
```
## License Header
```shell script
./mvnw license:format -Dgpg.skip
```
