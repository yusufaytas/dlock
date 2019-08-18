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
package com.yusufaytas.dlock.jdbc;

import com.yusufaytas.dlock.core.LockConfig;
import com.yusufaytas.dlock.core.UnreachableLockException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

/**
 * A postgres implementation for interval lock. A full table lock isn't required as postgres is able to provide the same
 * safety mechanism through the constraints.
 */
public class PostgresIntervalLock extends JdbcIntervalLock {

  public PostgresIntervalLock(DataSource dataSource) {
    super(dataSource);
  }

  public PostgresIntervalLock(
      NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  public PostgresIntervalLock(DataSource dataSource, TableConfig tableConfig) {
    super(dataSource, tableConfig);
  }

  public PostgresIntervalLock(NamedParameterJdbcTemplate jdbcTemplate, TableConfig tableConfig) {
    super(jdbcTemplate, tableConfig);
  }

  private String getSafeName(String name) {
    return "\"" + name + "\"";
  }

  private String getValueName(String name) {
    return ":" + name;
  }

  @Override
  protected String buildTryLockSql(TableConfig tableConfig) {
    return new StringBuilder().append("INSERT INTO ")
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
        .append(")\n")
        .append("VALUES(")
        .append(getValueName(tableConfig.getName()))
        .append(", ")
        .append(getValueName(tableConfig.getOwner()))
        .append(", NOW(), ")
        .append("NOW() + :" + tableConfig.getTill() + " * interval '1 second'")
        .append(")\n")
        .append("ON CONFLICT DO NOTHING")
        .toString();
  }

  @Override
  public boolean tryLock(final LockConfig lockConfig) throws UnreachableLockException {
    final MapSqlParameterSource parameterSource = getParams(lockConfig);
    final KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(tryLockSql, parameterSource, keyHolder);
    return !(keyHolder.getKeys() == null || keyHolder.getKeys().isEmpty());
  }

}
