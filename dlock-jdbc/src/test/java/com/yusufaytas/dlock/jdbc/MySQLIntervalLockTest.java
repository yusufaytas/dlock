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
