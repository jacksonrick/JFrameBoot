package com.jf.db;

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
 * 生成数据字典(HTML)
 * @author xujunfei
 * @version 1.0
 * @date 2016-11-02 10:56
 *
 */
public class GenerateDict {
	// 数据库名称
	private final String dbName = "jframe";

	// 输出路径
	private final String global_path = "/Users/xujunfei/Desktop/Project/db";

	// 数据库连接信息
	private final String driverName = "com.mysql.jdbc.Driver";

	private final String user = "root";

	private final String password = "12345678";

	private final String url = "jdbc:mysql://127.0.0.1:3306/" + dbName + "?characterEncoding=utf8&useSSL=false";

	private String tableName = null;

	private Connection conn = null;

	/**
	 * 初始化连接
	 * 
	 * @author rick
	 * @date 2016年8月10日 上午8:54:10
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	/**
	 * 获取所有的表
	 *
	 * @return
	 * @throws SQLException
	 */
	private List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		PreparedStatement pstate = conn.prepareStatement("show tables");
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
		PreparedStatement pstate = conn.prepareStatement("show table status");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString("NAME");
			String comment = results.getString("COMMENT");
			maps.put(tableName, comment);
		}
		return maps;
	}

	public void generate() throws ClassNotFoundException, SQLException, IOException {
		init();
		String prefix = "show full fields from ";
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
		exportFile(html);
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

	private void exportFile(String content) throws IOException {
		File folder = new File(global_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File File = new File(global_path, dbName + "_dict.html");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(File)));
		bw.write(content);
		bw.flush();
		bw.close();
	}

	public static void main(String[] args) {
		System.out.println("开始执行......");
		long start = System.currentTimeMillis();
		try {
			new GenerateDict().generate();
			System.out.println("执行完成，总用时 : " + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("执行出现问题，请检查");
		}
	}

}
