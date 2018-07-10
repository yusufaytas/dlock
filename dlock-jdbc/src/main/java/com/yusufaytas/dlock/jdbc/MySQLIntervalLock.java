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

import com.yusufaytas.dlock.core.LockConfig;
import com.yusufaytas.dlock.core.UnreachableLockException;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

public class MySQLIntervalLock extends JdbcIntervalLock {

  private final String lockTableSql;
  private final TransactionTemplate transactionTemplate;

  public MySQLIntervalLock(DataSource dataSource) {
    super(dataSource);
    this.lockTableSql = buildLockTableSql(tableConfig);
    this.transactionTemplate = new TransactionTemplate(
        new DataSourceTransactionManager(dataSource));
  }

  public MySQLIntervalLock(
      NamedParameterJdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
    super(jdbcTemplate);
    this.lockTableSql = buildLockTableSql(tableConfig);
    this.transactionTemplate = new TransactionTemplate(transactionManager);
  }

  public MySQLIntervalLock(DataSource dataSource,
      TableConfig tableConfig) {
    super(dataSource, tableConfig);
    this.lockTableSql = buildLockTableSql(tableConfig);
    this.transactionTemplate = new TransactionTemplate(
        new DataSourceTransactionManager(dataSource));
  }

  public MySQLIntervalLock(NamedParameterJdbcTemplate jdbcTemplate,
      DataSourceTransactionManager transactionManager, TableConfig tableConfig) {
    super(jdbcTemplate, tableConfig);
    this.lockTableSql = buildLockTableSql(tableConfig);
    this.transactionTemplate = new TransactionTemplate(transactionManager);
  }

  private String getSafeName(String name) {
    return "`" + name + "`";
  }

  private String getValueName(String name) {
    return ":" + name;
  }

  protected String buildLockTableSql(TableConfig tableConfig) {
    return new StringBuilder()
        .append("LOCK TABLES ")
        .append(getSafeName(tableConfig.getSchema()))
        .append(".")
        .append(getSafeName(tableConfig.getTable()))
        .append(" WRITE, ")
        .append(getSafeName(tableConfig.getSchema()))
        .append(".")
        .append(getSafeName(tableConfig.getTable()))
        .append(" AS dlock_read READ;")
        .toString();
  }

  @Override
  protected String buildTryLockSql(TableConfig tableConfig) {
    return new StringBuilder()
        .append("INSERT INTO ")
        .append(getSafeName(tableConfig.getSchema()))
        .append(".")
        .append(getSafeName(tableConfig.getTable()))
        .append(" (")
        .append(getSafeName(tableConfig.getName()))
        .append(", ")
        .append(getSafeName(tableConfig.getOwner()))
        .append(", ")
        .append(getSafeName(tableConfig.getAt()))
        .append(", ")
        .append(getSafeName(tableConfig.getTill()))
        .append(")\nSELECT ")
        .append(getValueName(tableConfig.getName()))
        .append(", ")
        .append(getValueName(tableConfig.getOwner()))
        .append(", CURRENT_TIMESTAMP(), ")
        .append("CURRENT_TIMESTAMP() + INTERVAL " + getValueName(tableConfig.getTill()) + " SECOND")
        .append("\nFROM dual")
        .append("\nWHERE CURRENT_TIMESTAMP() > (SELECT COALESCE(MAX(")
        .append(getSafeName(tableConfig.getTill()))
        .append("), '1923-10-29') FROM ")
        .append(getSafeName(tableConfig.getSchema()))
        .append(".")
        .append(getSafeName(tableConfig.getTable()))
        .append(" AS dlock_read WHERE ")
        .append(getSafeName(tableConfig.getName()) + "=" + getValueName(tableConfig.getName()))
        .append(")")
        .toString();
  }

  @Override
  public boolean tryLock(final LockConfig lockConfig) throws UnreachableLockException {
    return transactionTemplate.execute(transactionStatus -> {
      MapSqlParameterSource parameterSource = getParams(lockConfig);
      KeyHolder keyHolder = new GeneratedKeyHolder();
      try {
        jdbcTemplate.update(lockTableSql, parameterSource);
        jdbcTemplate.update(tryLockSql, parameterSource, keyHolder);
      } finally {
        jdbcTemplate.update("UNLOCK TABLES", parameterSource);
      }
      return !(keyHolder.getKeys() == null || keyHolder.getKeys().isEmpty());
    }).booleanValue();
  }

}
