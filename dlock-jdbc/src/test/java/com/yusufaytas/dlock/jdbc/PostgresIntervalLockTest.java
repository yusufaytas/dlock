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

public class PostgresIntervalLockTest {

  @Mock
  DataSource dataSource;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void builtTryLockSql()
      throws IOException {
    PostgresIntervalLock postgresIntervalLock = new PostgresIntervalLock(dataSource);
    String tryLockSQL = postgresIntervalLock.buildTryLockSql(new TableConfig());
    assertEquals(
        "INSERT INTO \"public\".\"lock\" (\"name\", \"owner\", \"locked_at\", \"locked_till\")\n"
            + "VALUES(:name, :owner, NOW(), NOW() + :locked_till * interval '1 second')\n"
            + "ON CONFLICT DO NOTHING", tryLockSQL);
  }
}
