package com.jf.generate;

/**
 * Created with IntelliJ IDEA.
 * Description: 数据库生成工具配置类
 * User: xujunfei
 * Date: 2018-06-15
 * Time: 11:42
 */
public class GenInfo {

    private String dbName;
    private String dbUrl;
    private String driver;
    private String schema;
    private String username;
    private String password;
    private String globalPath;
    private String author;

    public GenInfo name(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public GenInfo url(String dbUrl) {
        this.dbUrl = dbUrl;
        return this;
    }

    public GenInfo driver(String driver) {
        this.driver = driver;
        return this;
    }

    public GenInfo schema(String schema) {
        this.schema = schema;
        return this;
    }

    public GenInfo username(String username) {
        this.username = username;
        return this;
    }

    public GenInfo password(String password) {
        this.password = password;
        return this;
    }

    public GenInfo path(String globalPath) {
        this.globalPath = globalPath;
        return this;
    }

    public GenInfo author(String author) {
        this.author = author;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGlobalPath() {
        return globalPath;
    }

    public void setGlobalPath(String globalPath) {
        this.globalPath = globalPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
