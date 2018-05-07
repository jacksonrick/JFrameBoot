package com.jf.system.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL SQL执行器
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DbSQLExecutor {

    private static String url;
    private static String username;
    private static String password;

    /**
     * @return 获取conn对象
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConn() throws ClassNotFoundException, SQLException {
        return DbSQLExecutor.getCon(username, password, url);
    }

    /**
     * @param username
     * @param password
     * @param dburl
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getCon(String username, String password, String dburl) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, username, password);
    }

    /**
     * 字段名列表
     *
     * @param conn
     * @param table
     * @return
     * @throws SQLException
     */
    public static List<String> getFieldLsit(Connection conn, String table) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(table);
        pstmt.execute();
        List<String> columnList = new ArrayList<String>();    //存放字段
        ResultSetMetaData rsmd = pstmt.getMetaData();
        for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
            columnList.add(rsmd.getColumnName(i));            //把字段名放list里
        }
        return columnList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
