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
