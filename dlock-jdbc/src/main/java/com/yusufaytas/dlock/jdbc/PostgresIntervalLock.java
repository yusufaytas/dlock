package com.yusufaytas.dlock.jdbc;

import com.yusufaytas.dlock.core.LockConfig;
import com.yusufaytas.dlock.core.UnreachableLockException;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class PostgresIntervalLock extends JdbcIntervalLock {

  private String tryLockSql;

  public PostgresIntervalLock(DataSource dataSource) {
    super(dataSource);
    buildTryLockSql(tableConfig);
  }

  public PostgresIntervalLock(
      NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
    buildTryLockSql(tableConfig);
  }

  public PostgresIntervalLock(DataSource dataSource, TableConfig tableConfig) {
    super(dataSource, tableConfig);
    buildTryLockSql(tableConfig);
  }

  public PostgresIntervalLock(NamedParameterJdbcTemplate jdbcTemplate, TableConfig tableConfig) {
    super(jdbcTemplate, tableConfig);
    buildTryLockSql(tableConfig);
  }

  private String getSafeName(String name) {
    return "\"" + name + "\"";
  }

  private String getValueName(String name) {
    return ":" + name;
  }

  private void buildTryLockSql(TableConfig tableConfig) {
    tryLockSql = new StringBuilder().append("INSERT INTO ")
        .append(getSafeName(tableConfig.getSchema()))
        .append(".")
        .append(getSafeName(tableConfig.getTable()))
        .append(" (")
        .append(getSafeName(tableConfig.getName()))
        .append(",")
        .append(getSafeName(tableConfig.getOwner()))
        .append(",")
        .append(getSafeName(tableConfig.getAt()))
        .append(",")
        .append(getSafeName(tableConfig.getTill()))
        .append(")\n")
        .append("VALUES(")
        .append(getValueName(tableConfig.getName()))
        .append(",")
        .append(getValueName(tableConfig.getOwner()))
        .append(",NOW(),")
        .append("NOW() + :" + tableConfig.getTill() + "* interval '1 second'")
        .append(")\n")
        .append("ON CONFLICT DO NOTHING")
        .toString();
  }

  @Override
  public boolean tryLock(LockConfig lockConfig) throws UnreachableLockException {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue(tableConfig.getName(), lockConfig.getName());
    parameterSource.addValue(tableConfig.getOwner(), lockConfig.getOwner());
    parameterSource.addValue(tableConfig.getTill(), lockConfig.getDuration());
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(tryLockSql, parameterSource, keyHolder);
    return !(keyHolder.getKeys() == null || keyHolder.getKeys().isEmpty());
  }
}
