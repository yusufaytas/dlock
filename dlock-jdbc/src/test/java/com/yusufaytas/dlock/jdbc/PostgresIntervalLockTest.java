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
