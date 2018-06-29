package com.yusufaytas.dlock.jdbc;

/**
 * TableConfig specifies the column names for expected lock table columns.
 */
public class TableConfig {

  private String schema = "public";
  private String table = "lock";
  private String id = "id";
  private String at = "locked_at";
  private String till = "locked_till";
  private String name = "name";
  private String owner = "owner";

  public TableConfig() {
  }

  public TableConfig(String id, String schema, String table, String at, String till, String name,
      String owner) {
    this.id = id;
    this.schema = schema;
    this.table = table;
    this.at = at;
    this.till = till;
    this.name = name;
    this.owner = owner;
  }

  public String getId() {
    return id;
  }

  public TableConfig setId(String id) {
    this.id = id;
    return this;
  }

  public String getSchema() {
    return schema;
  }

  public TableConfig setSchema(String schema) {
    this.schema = schema;
    return this;
  }

  public String getTable() {
    return table;
  }

  public TableConfig setTable(String table) {
    this.table = table;
    return this;
  }

  public String getAt() {
    return at;
  }

  public TableConfig setAt(String at) {
    this.at = at;
    return this;
  }

  public String getTill() {
    return till;
  }

  public TableConfig setTill(String till) {
    this.till = till;
    return this;
  }

  public String getName() {
    return name;
  }

  public TableConfig setName(String name) {
    this.name = name;
    return this;
  }

  public String getOwner() {
    return owner;
  }

  public TableConfig setOwner(String owner) {
    this.owner = owner;
    return this;
  }
}
