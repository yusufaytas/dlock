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

import com.yusufaytas.dlock.core.IntervalLock;
import com.yusufaytas.dlock.core.LockConfig;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class JdbcIntervalLock implements IntervalLock {

  protected String tryLockSql;
  protected TableConfig tableConfig;
  protected NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcIntervalLock(DataSource dataSource) {
    this(new NamedParameterJdbcTemplate(dataSource), new TableConfig());
  }

  public JdbcIntervalLock(NamedParameterJdbcTemplate jdbcTemplate) {
    this(jdbcTemplate, new TableConfig());
  }

  public JdbcIntervalLock(DataSource dataSource, TableConfig tableConfig) {
    this(new NamedParameterJdbcTemplate(dataSource), tableConfig);
  }

  public JdbcIntervalLock(NamedParameterJdbcTemplate jdbcTemplate, TableConfig tableConfig) {
    this.jdbcTemplate = jdbcTemplate;
    this.tableConfig = tableConfig;
    this.tryLockSql = buildTryLockSql(tableConfig);
  }

  protected MapSqlParameterSource getParams(LockConfig lockConfig) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue(tableConfig.getName(), lockConfig.getName());
    parameterSource.addValue(tableConfig.getOwner(), lockConfig.getOwner());
    parameterSource.addValue(tableConfig.getTill(), lockConfig.getDuration());
    return parameterSource;
  }

  protected abstract String buildTryLockSql(TableConfig tableConfig);
}
