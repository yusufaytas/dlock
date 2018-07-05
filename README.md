DLock
========
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt) [![Build Status](https://travis-ci.org/yusufaytas/dlock.png?branch=master)](https://travis-ci.org/yusufaytas/dlock)

DLock is a library to get a distributed interval lock. An Interval Lock is a lock where the acquirer acquires the lock for a certain amount of time. Once the lock is acquired, it won't be released till the interval ends.

Feedback and pull-requests are welcome!
+ [Usage](#usage)
+ [Lock Implementations](#lock-implementations)
  - [Postgres](#postgres)
+ [Contributing](#contributing)

## Usage
### Requirements and dependencies
* Java 8
* slf4j-api

Note that, there should be only one global lock.

### Import project
#### maven
```xml
<dependency>
    <groupId>com.yusufaytas.dlock</groupId>
    <artifactId>dlock-spring</artifactId>
    <version>0.1.0</version>
</dependency>
```
#### gradle
```groovy
testCompile 'com.yusufaytas.dlock:dlock-spring:0.1.0'
```
## Lock Implementations
### Postgres
We insert into postgres if there doesn't exist a lock. 
