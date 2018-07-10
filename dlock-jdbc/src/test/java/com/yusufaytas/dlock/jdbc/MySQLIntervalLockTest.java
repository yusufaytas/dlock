/**
 * Copyright © 2018 Yusuf Aytas. All rights reserved.
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
package com.yusufaytas.dlock.jdbc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MySQLIntervalLockTest {

  @Mock
  DataSource dataSource;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void builtLockTableSql()
      throws IOException {
    MySQLIntervalLock mySQLIntervalLock = new MySQLIntervalLock(dataSource);
    String lockTableSql = mySQLIntervalLock.buildLockTableSql(new TableConfig());
    assertEquals(
        "LOCK TABLES `public`.`lock` WRITE, `public`.`lock` AS dlock_read READ;", lockTableSql);
  }

  @Test
  public void builtTryLockSql()
      throws IOException {
    MySQLIntervalLock mySQLIntervalLock = new MySQLIntervalLock(dataSource);
    String tryLockSQL = mySQLIntervalLock.buildTryLockSql(new TableConfig());
    assertEquals(
        "INSERT INTO `public`.`lock` (`name`, `owner`, `locked_at`, `locked_till`)\n"
            + "SELECT :name, :owner, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP() + INTERVAL :locked_till SECOND\n"
            + "FROM dual\n"
            + "WHERE CURRENT_TIMESTAMP() > (SELECT COALESCE(MAX(`locked_till`), '1923-10-29') "
            + "FROM `public`.`lock` AS dlock_read WHERE `name`=:name)", tryLockSQL);
  }
}
