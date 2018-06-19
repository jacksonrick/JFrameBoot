package com.jf.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成数据字典
 * <p>仅支持MySQL</p>
 *
 * @author xujunfei
 * @version 2.0
 * @date 2016-11-02 10:56
 */
public class GenerateDict {

    private String dbName = "";
    private String tableName = null;
    private Connection conn = null;

    /**
     * 初始化连接
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @author rick
     * @date 2016年8月10日 上午8:54:10
     */
    private void init(GenInfo info) throws ClassNotFoundException, SQLException {
        Class.forName(info.getDriver());
        conn = DriverManager.getConnection(info.getDbUrl(), info.getUsername(), info.getPassword());
        dbName = info.getDbName();
    }

    /**
     * 获取所有的表
     *
     * @return
     * @throws SQLException
     */
    private List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pstate = conn.prepareStatement("SHOW TABLES");
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
        Map<String, String> maps = new HashMap<String, String>();
        PreparedStatement pstate = conn.prepareStatement("SHOW TABLE STATUS");
        ResultSet results = pstate.executeQuery();
        while (results.next()) {
            String tableName = results.getString("NAME");
            String comment = results.getString("COMMENT");
            maps.put(tableName, comment);
        }
        return maps;
    }

    /**
     * @param info
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public void generate(GenInfo info) throws ClassNotFoundException, SQLException, IOException {
        this.init(info);

        String prefix = "SHOW FULL FIELDS FROM ";
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
            pstate = conn.prepareStatement(prefix + table);
            ResultSet results = pstate.executeQuery();
            while (results.next()) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                keys.add(results.getString("KEY"));
                nulls.add(results.getString("NULL"));
                defaults.add(results.getString("DEFAULT"));
                extras.add(results.getString("EXTRA"));
                comments.add(results.getString("COMMENT"));
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
            + "</head><body><div class=\"container\"><h1 style=\"text-align:center;\">" + dbName + " 数据字典</h1>";

    private void buildFile(List<String> columns, List<String> types, List<String> keys, List<String> nulls, List<String> defaults,
                           List<String> extras, List<String> comments, String tableComment) {
        html += "<h3>" + tableName + " " + tableComment + "</h3>";
        html += "<table class=\"table table-hover table-bordered table-condensed\"><thead><tr>"
                + "<th>字段名</th><th>数据类型</th><th>主外键</th><th>允许空</th><th>默认值</th><th>自动递增</th><th>备注</th>"
                + "</tr></thead><tbody>";
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            html += "<tr>";
            html += "<td>" + columns.get(i) + "</td><td>" + types.get(i) + "</td><td>" + keys.get(i) + "</td><td>" + nulls.get(i)
                    + "</td><td>" + defaults.get(i) + "</td><td>" + extras.get(i) + "</td><td>" + comments.get(i) + "</td>";
            html += "</tr>";
        }
        html += "</tbody></table>";
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
        File file = new File(target, dbName + "_dict.html");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(content);
        bw.flush();
        bw.close();
        System.out.println("文件输出到：" + file.getAbsolutePath());
    }

}
