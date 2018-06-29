package com.yusufaytas.dlock.jdbc;

import com.yusufaytas.dlock.core.IntervalLock;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class JdbcIntervalLock implements IntervalLock {

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
  }

}
