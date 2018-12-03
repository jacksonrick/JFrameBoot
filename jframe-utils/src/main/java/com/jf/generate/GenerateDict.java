package com.jf.generate;

import java.io.*;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成数据字典
 * <p>支持MySQL & Postgresql</p>
 *
 * @author xujunfei
 * @version 3.0
 * @date 2016-11-02 10:56
 */
public class GenerateDict {

    private String tableName = null;
    private String schema = null;
    private Connection conn = null;
    private Integer dbType = 1; // 1-mysql 2-postgres

    /**
     * 初始化连接
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @author rick
     * @date 2016年8月10日 上午8:54:10
     */
    private void init(GenInfo info) throws ClassNotFoundException, SQLException {
        if (info.getDriver().contains("mysql")) {
            System.out.println("数据库类型：mysql");
            dbType = 1;
        } else if (info.getDriver().contains("postgresql")) {
            System.out.println("数据库类型：postgresql");
            dbType = 2;
            schema = info.getSchema();
            if (schema == null) {
                throw new RuntimeException("postgres必须指定schema");
            }
        } else {
            throw new RuntimeException("不支持的数据库");
        }

        Class.forName(info.getDriver());
        conn = DriverManager.getConnection(info.getDbUrl(), info.getUsername(), info.getPassword());
    }

    /**
     * 获取所有的表
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTables() throws SQLException {
        String sql = "";
        if (dbType == 1) {
            sql = "SHOW TABLES";
        } else if (dbType == 2) {
            sql = "SELECT tablename FROM pg_tables WHERE schemaname = '" + schema + "'";
        }
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement(sql);
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString(1);
            tables.add(tableName);
        }
        return tables;
    }

    /**
     * 获取所有的数据库表注释
     *
     * @return
     * @throws SQLException
     */
    private Map<String, String> getTableComment() throws SQLException {
        String sql = "";
        if (dbType == 1) {
            sql = "SHOW TABLE STATUS";
        } else if (dbType == 2) {
            sql = "SELECT C.relname AS NAME, CAST ( obj_description ( C.relfilenode, 'pg_class' ) AS VARCHAR ) AS COMMENT \n" +
                    "FROM pg_class C, pg_tables T WHERE T.schemaname = '" + schema + "' AND C.relname = T.tablename AND relkind = 'r'";
        }
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement(sql);
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }

    /**
     * 处理字段
     * user_id -> userId
     *
     * @param field
     * @return
     * @author rick
     * @date 2016年8月10日 上午8:55:21
     */
    private String processField(String field) {
        StringBuffer sb = new StringBuffer(field.length());
        field = field.toLowerCase();
        String[] fields = field.split("_");
        String temp = null;
        sb.append(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }

    /**
     * @param info
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public void generate(GenInfo info) throws ClassNotFoundException, SQLException, IOException {
        this.init(info);

        String sql = "";
        if (dbType == 1) {
            sql = "SHOW FULL FIELDS FROM {0}";
        } else if (dbType == 2) {
            sql = "SELECT col.column_name AS \"FIELD\", col.is_nullable AS \"NULL\", col.udt_name AS \"TYPE\", col.character_maximum_length AS \"LCHAR\", col.numeric_precision AS \"LNUM\", col.numeric_scale AS \"LNUMC\", col.datetime_precision AS \"LDATE\", col_description(c.oid, col.ordinal_position) AS \"COMMENT\", col.column_default AS \"DEFAULT\" FROM information_schema.columns AS col LEFT JOIN pg_namespace ns ON ns.nspname = col.table_schema \n" +
                    "LEFT JOIN pg_class c ON col.table_name = c.relname AND c.relnamespace = ns.oid \n" +
                    "WHERE col.table_name = ''{0}''";
        }

        List<String> columns = null;
        List<String> types = null;
        List<String> keys = null;
        List<String> nulls = null;
        List<String> defaults = null;
        List<String> extras = null;
        List<String> comments = null;

        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();
        for (String table : tables) {
            columns = new ArrayList<String>();
            types = new ArrayList<String>();
            keys = new ArrayList<String>();
            nulls = new ArrayList<String>();
            defaults = new ArrayList<String>();
            extras = new ArrayList<String>();
            comments = new ArrayList<String>();
            pstate = conn.prepareStatement(MessageFormat.format(sql, table));
            ResultSet results = pstate.executeQuery();
            while (results.next()) {
                if (dbType == 1) {
                    columns.add(results.getString("FIELD"));
                    types.add(results.getString("TYPE"));
                    keys.add(results.getString("KEY"));
                    nulls.add(results.getString("NULL"));
                    defaults.add(results.getString("DEFAULT"));
                    extras.add(results.getString("EXTRA"));
                    comments.add(results.getString("COMMENT"));
                } else if (dbType == 2) {
                    columns.add(results.getString("FIELD"));
                    nulls.add(results.getString("NULL"));
                    comments.add(results.getString("COMMENT"));
                    defaults.add(results.getString("DEFAULT"));
                    String type = results.getString("TYPE");
                    types.add(type);
                    if (type.indexOf("char") > -1) {
                        extras.add(results.getString("LCHAR"));
                    } else if (type.indexOf("int") > -1) {
                        extras.add(results.getString("LNUM"));
                    } else if (type.indexOf("numeric") > -1) {
                        extras.add(results.getString("LNUM") + ":" + results.getString("LNUMC"));
                    } else if (type.indexOf("time") > -1 || type.indexOf("date") > -1) {
                        extras.add(results.getString("LDATE"));
                    } else {
                        extras.add(" ");
                    }
                }
            }
            tableName = table;
            String tableComment = tableComments.get(tableName);
            buildFile(columns, types, keys, nulls, defaults, extras, comments, tableComment);
        }
        html += "</div></body></html>";
        exportFile(info.getGlobalPath(), html);
        conn.close();
    }

    String html = "<!doctype html><html><head><meta charset=\"UTF-8\"><title>数据字典</title>"
            + "<link href=\"http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css\" rel=\"stylesheet\">"
            + "</head><body><div class=\"container\"><h1 style=\"text-align:center;\">数据字典</h1>";

    private void buildFile(List<String> columns, List<String> types, List<String> keys, List<String> nulls, List<String> defaults,
                           List<String> extras, List<String> comments, String tableComment) {
        html += "<h3>" + tableName + " " + tableComment + "</h3>";
        if (dbType == 1) {
            html += "<table class=\"table table-hover table-bordered table-condensed\"><thead><tr>"
                    + "<th>数据库字段</th><th>JSON字段</th><th>数据类型</th><th>主外键</th><th>允许空</th><th>默认值</th><th>自动递增</th><th>备注</th>"
                    + "</tr></thead><tbody>";
            int size = columns.size();
            for (int i = 0; i < size; i++) {
                html += "<tr>";
                html += "<td>" + columns.get(i) + "</td><td>" + processField(columns.get(i)) + "</td><td>" + types.get(i) + "</td><td>" + keys.get(i) + "</td><td>" + nulls.get(i)
                        + "</td><td>" + defaults.get(i) + "</td><td>" + extras.get(i) + "</td><td>" + comments.get(i) + "</td>";
                html += "</tr>";
            }
            html += "</tbody></table>";
        } else if (dbType == 2) {
            html += "<table class=\"table table-hover table-bordered table-condensed\"><thead><tr>"
                    + "<th>数据库字段</th><th>JSON字段</th><th>数据类型</th><th>数据长度</th><th>允许空</th><th>默认值</th><th>备注</th>"
                    + "</tr></thead><tbody>";
            int size = columns.size();
            for (int i = 0; i < size; i++) {
                html += "<tr>";
                html += "<td>" + columns.get(i) + "</td><td>" + processField(columns.get(i)) + "</td><td>" + types.get(i) + "</td><td>" + extras.get(i)
                        + "</td><td>" + nulls.get(i) + "</td><td>" + defaults.get(i) + "</td><td>" + comments.get(i) + "</td>";
                html += "</tr>";
            }
            html += "</tbody></table>";
        }
    }

    /**
     * @param target
     * @param content
     * @throws IOException
     */
    private void exportFile(String target, String content) throws IOException {
        File folder = new File(target);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(target, "dict.html");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(content);
        bw.flush();
        bw.close();
        System.out.println("文件输出到：" + file.getAbsolutePath());
    }

}
