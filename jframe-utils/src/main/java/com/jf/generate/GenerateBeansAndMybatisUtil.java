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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动生成MyBatis的实体类、实体映射XML文件、Mapper<br>
 * *对于mybatis xml还需进一步修改使用，实体类可直接使用<br>
 * *此类仅对本项目有效，其他项目规范可适当修改参数使用
 *
 * @version 4.0
 * @date 2018-01-18
 */
public class GenerateBeansAndMybatisUtil {

    /***************************************
     * 注意数据库表名称，如：t_user、t_user_info
     * 类型应如：int、tinyint、decimal、text、char、varchar、datetime
     *************************************/

    private static String type_char = "char";

    private static String type_date = "date";

    private static String type_timestamp = "timestamp";

    private static String type_int = "int";

    private static String type_bigint = "bigint";

    private static String type_tinyint = "tinyint";

    private static String type_text = "text";

    private static String type_decimal = "decimal";

    private static String type_double = "double";

    private static String type_bit = "bit";

    private static String type_blob = "blob";

    // 数据库名称
    private static String dbName = "jframe";

    // 输出路径
    private static String global_path = "/Users/xujunfei/Desktop/Project/db/mybatis/";

    private static String bean_path = global_path + "entity_bean";

    private static String mapper_path = global_path + "entity_mapper";

    private static String xml_path = global_path + "entity_mapper/xml";

    private static String html_path = global_path + "html";

    // 包名
    private static String bean_package = "com.jf.model";

    private static String mapper_package = "com.jf.mapper";

    // 基类
    private static String base_vo = "com.jf.model.custom.BaseVo;";

    // 数据库连接信息
    private static String driverName = "com.mysql.jdbc.Driver";

    private static String user = "root";

    private static String password = "12345678";

    private static String url = "jdbc:mysql://127.0.0.1:3306/" + dbName + "?characterEncoding=utf8&useSSL=false";

    private String tableName = null;
    private String beanName = null;
    private String mapperName = null;
    private Connection conn = null;

    // 作者&时间
    private final String author = "jfxu";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 aaa HH:mm:ss");

    /**
     * 初始化连接
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @author rick
     * @date 2016年8月10日 上午8:54:10
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
     * 处理字段类型
     *
     * @param type
     * @return
     * @author rick
     * @date 2016年8月10日 上午8:54:55
     */
    private String processType(String type) {
        if (type.indexOf(type_char) > -1) {
            return "String";
        } else if (type.indexOf(type_bigint) > -1) {
            return "Long";
        } else if (type.indexOf(type_tinyint) > -1) {
            return "Integer";
        } else if (type.indexOf(type_int) > -1) {
            return "Integer";
        } else if (type.indexOf(type_date) > -1) {
            return "Date";
        } else if (type.indexOf(type_text) > -1) {
            return "String";
        } else if (type.indexOf(type_timestamp) > -1) {
            return "Date";
        } else if (type.indexOf(type_bit) > -1) {
            return "Boolean";
        } else if (type.indexOf(type_decimal) > -1) {
            return "Double";
        } else if (type.indexOf(type_double) > -1) {
            return "Double";
        } else if (type.indexOf(type_blob) > -1) {
            return "byte[]";
        }
        return "String";
    }

    /**
     * 处理表
     *
     * @param table
     * @author rick
     * @date 2016年8月10日 上午8:53:56
     */
    private void processTable(String table) {
        StringBuffer sb = new StringBuffer(table.length());
        String tableNew = table.toLowerCase();
        String[] tables = tableNew.split("_"); // 去除下划线之前的内容
        String temp = null;
        for (int i = 1; i < tables.length; i++) {
            temp = tables[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        beanName = sb.toString();
        mapperName = beanName + "Mapper";
    }

    /**
     * 处理字段
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
     * 处理html文件名
     *
     * @param table
     * @return
     * @author rick
     * @date 2016年8月10日 上午8:55:21
     */
    private String processHtmlFile(String table) {
        return table.substring(table.indexOf("_") + 1, table.length());
    }

    /**
     * 将实体类名首字母改为小写
     *
     * @param beanName
     * @return
     */
    private String processResultMapId(String beanName) {
        return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    }

    /**
     * 构建类上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedWriter buildClassComment(BufferedWriter bw, String text) throws IOException {
        bw.newLine();
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" * " + text);
        bw.newLine();
        bw.write(" * @date " + simpleDateFormat.format(new Date()));
        bw.newLine();
        bw.write(" * @author " + author);
        bw.newLine();
        bw.write(" */");
        return bw;
    }

    /**
     * 构建方法上面的注释
     *
     * @param bw
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedWriter buildMethodComment(BufferedWriter bw, String text) throws IOException {
        bw.newLine();
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * " + text);
        bw.newLine();
        bw.write("\t */");
        return bw;
    }

    /**
     * @param size
     * @param types
     * @return
     */
    private Boolean checkDate(int size, List<String> types) {
        for (int i = 0; i < size; i++) {
            if (processType(types.get(i)).equals("Date")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成html表单和列表
     * <p>基于h5 bootstrap</p>
     *
     * @param columns
     * @param types
     * @param tableComment
     */
    private void buildForm(List<String> columns, List<String> types, List<String> comments, String tableComment) throws IOException {
        File folder = new File(html_path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File htmlEditFile = new File(html_path, processHtmlFile(tableName) + "_edit.ftl");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlEditFile)));
        bw.write("<!DOCTYPE html>\n<html>\n<head>\n\t<meta charset=\"utf-8\">\n\t<title>" + tableComment + "</title>");
        bw.newLine();
        bw.write("<#include \"include.ftl\"/>\n</head>");
        bw.newLine();
        bw.write("<body>\n\n<div class=\"ibox-content\">\n\t<form class=\"form-horizontal\" id=\"" + beanName + "EditForm\">");
        bw.newLine();

        int size = columns.size();
        Boolean hasDate = false;
        String dateName = "";
        for (int i = 0; i < size; i++) {
            bw.write("\t\t<div class=\"form-group\">");
            bw.newLine();
            bw.write("\t\t\t<label class=\"col-sm-2 control-label\">" + comments.get(i) + "：</label>");
            bw.newLine();
            bw.write("\t\t\t<div class=\"col-sm-9\">");
            bw.newLine();
            String name = processField(columns.get(i));
            String comment = comments.get(i);
            if ("Boolean".equals(processType(types.get(i)))) {
                bw.write("\t\t\t\t<div class=\"radio radio-info radio-inline\">");
                bw.newLine();
                bw.write("\t\t\t\t\t<input type=\"radio\" name=\"" + name + "\" id=\"" + name + "1\" value=\"1\" ${prefix." + name + "?string('checked','') }>\n\t\t\t\t\t<label for=\"" + name + "1\">1</label>");
                bw.newLine();
                bw.write("\t\t\t\t</div>\n\t\t\t\t<div class=\"radio radio-info radio-inline\">");
                bw.newLine();
                bw.write("\t\t\t\t\t<input type=\"radio\" name=\"" + name + "\" id=\"" + name + "0\" value=\"0\" ${prefix." + name + "?string('checked','') }>\n\t\t\t\t\t<label for=\"" + name + "0\">0</label>\n\t\t\t\t</div>");
            } else if ("Date".equals(processType(types.get(i)))) {
                bw.write("\t\t\t\t<input type=\"text\" id=\"" + name + "\" name=\"" + name + "\" value=\"${prefix." + name + " }\" class=\"form-control\" placeholder=\"" + comment + "\">");
                hasDate = true;
                dateName = name;
            } else {
                bw.write("\t\t\t\t<input type=\"text\" name=\"" + name + "\" value=\"${prefix." + name + " }\" class=\"form-control\" placeholder=\"" + comment + "\">");
            }
            bw.newLine();
            bw.write("\t\t\t</div>\n\t\t</div>");
            bw.newLine();
        }

        bw.write("\t\t<div class=\"form-group\">\n\t\t\t<div class=\"col-sm-offset-2 col-sm-3\">");
        bw.newLine();
        bw.write("\t\t\t\t<input type=\"hidden\" name=\"id\" value=\"${prefix.id }\">");
        bw.newLine();
        bw.write("\t\t\t\t<button type=\"submit\" class=\"btn btn-info btn-block\">保存</button>");
        bw.newLine();
        bw.write("\t\t\t</div>\n\t\t</div>\n\t</form>\n</div>");
        bw.newLine();
        bw.newLine();

        bw.write("<script type=\"text/javascript\">");
        bw.newLine();
        bw.write("\t$(function () {");
        bw.newLine();
        if (hasDate) {
            bw.write("\t\tdatePicker(\"#" + dateName + "\", \"yyyy-mm-dd\", 2);");
            bw.newLine();
        }
        bw.write("\t\tvar index = parent.layer.getFrameIndex(window.name);");
        bw.newLine();
        bw.newLine();
        bw.write("\t\t$(\"#" + beanName + "EditForm\").bootstrapValidator({");
        bw.newLine();
        bw.write("\t\t\texcluded: [':disabled'],");
        bw.newLine();
        bw.write("\t\t\tfields: {");
        bw.newLine();
        for (int i = 0; i < size; i++) {
            bw.write("\t\t\t\t" + processField(columns.get(i)) + ": {\n\t\t\t\t\tvalidators: {");
            bw.newLine();
            bw.write("\t\t\t\t\t\tnotEmpty: {\n\t\t\t\t\t\t\tmessage: '不能为空'\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t},");
            bw.newLine();
        }
        bw.write("\t\t\t}\n\t\t}).on('success.form.bv', function (e) {\n\t\t\te.preventDefault();\n\t\t\tvar $form = $(e.target);");
        bw.newLine();
        bw.write("\t\t\tAjax.ajax({");
        bw.newLine();
        bw.write("\t\t\t\turl: '/admin/../..',\n\t\t\t\tparams: $form.serialize(),\n\t\t\t\tsuccess: function (data) {");
        bw.newLine();
        bw.write("\t\t\t\t\tif (data.code == 0) {\n\t\t\t\t\t\tshowMsg(data.msg, 1, function () {\n\t\t\t\t\t\t\tparent.layer.close(index);\n\t\t\t\t\t\t\tparent.reload();");
        bw.newLine();
        bw.write("\t\t\t\t\t\t});\n\t\t\t\t\t} else {\n\t\t\t\t\t\tshowMsg(data.msg, 2);\n\t\t\t\t\t}\n\t\t\t\t}");
        bw.newLine();
        bw.write("\t\t\t});");
        bw.newLine();
        bw.write("\t\t});");
        bw.newLine();
        bw.write("\t});");
        bw.newLine();
        bw.write("</script>");
        bw.newLine();
        bw.write("</body>\n</html>");

        bw.flush();
        bw.close();

        File htmlListFile = new File(html_path, processHtmlFile(tableName) + "_list.ftl");
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlListFile)));
        bw2.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title></title>\n" +
                "<#include \"include.ftl\"/>\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"gray-bg\">\n" +
                "<div class=\"ibox float-e-margins\">\n" +
                "    <div class=\"ibox-content\">\n" +
                "        <div class=\"row\">\n" +
                "            <div class=\"col-md-8\">\n" +
                "                <form class=\"form-inline\" method=\"post\" id=\"queryForm\">\n" +
                "                    <label> 关键字:<input type=\"text\" name=\"keywords\" class=\"form-control input-sm\"></label>\n" +
                "                    <label> 时间段:\n" +
                "                        <input type=\"text\" class=\"form-control input-sm\" id=\"startDate\" name=\"startDate\" placeholder=\"开始时间\">\n" +
                "                        <input type=\"text\" class=\"form-control input-sm\" id=\"endDate\" name=\"endDate\" placeholder=\"结束时间\">\n" +
                "                    </label>\n" +
                "                    <button type=\"button\" id=\"search\" class=\"btn btn-sm btn-info\"><i class=\"fa fa-search\"></i></button>\n" +
                "                    <button type=\"button\" id=\"export\" class=\"btn btn-sm btn-info\"><i class=\"fa fa-share\"></i>导出到Excel</button>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "            <div class=\"col-md-4 text-right\">\n" +
                "                <button type=\"button\" class=\"btn btn-primary btn-sm\" data-open=\"modal\" data-width=\"800px\" data-height=\"500px\" href=\"/admin/user/userDetail\" id=\"btn-add\">\n" +
                "                    <i class=\"fa fa-plus\"></i> 添加\n" +
                "                </button>\n" +
                "                <button type=\"button\" class=\"btn btn-danger btn-sm\" id=\"btn-del\"><i class=\"fa fa-remove\"></i> 批量删除</button>\n" +
                "                <button type=\"button\" class=\"btn btn-warning btn-sm\" data-open=\"modal\" data-width=\"800px\"\n" +
                "                        data-height=\"500px\" href=\"/admin/module/moduleList?mode=1\"><i class=\"fa fa-user\"></i> 选择数据\n" +
                "                </button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <table id=\"table\" class=\"table table-striped table-bordered table-hover\" width=\"100%\"></table>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "    var tables;\n" +
                "    $(function () {\n" +
                "        var columns = [\n" +
                "            CONSTANT.COLUMN.CHECKALL,\n");

        for (int i = 0; i < size; i++) {
            bw2.write("\t\t\t{title: \"" + comments.get(i) + "\", data: \"" + processField(columns.get(i)) + "\", defaultContent: \"--\"" + (i == 0 ? "" : ", orderable: false") + "},");
            bw2.newLine();
        }

        bw2.write("            {\n" +
                "                title: \"操作\", data: null, orderable: false,\n" +
                "                render: function (data, type, full, callback) {\n" +
                "                    var btns = CONSTANT.BUTTON.EDIT(\"/admin/module/moduleDetail?id=\" + full.id);\n" +
                "                    if (full.isDelete) {\n" +
                "                        btns += CONSTANT.BUTTON.ENABLE();\n" +
                "                    } else {\n" +
                "                        btns += CONSTANT.BUTTON.DISABLE();\n" +
                "                    }\n" +
                "                    return btns;\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "        var $table = $('#table');\n" +
                "        tables = $table.DataTable($.extend(true, {}, CONSTANT.DEFAULT_OPTION, {\n" +
                "            columns: columns,\n" +
                "            ajax: function (data, callback, settings) {\n" +
                "                CONSTANT.AJAX(\"/admin/module/moduleListData\", [[1, \"id\"]], data, callback, settings);\n" +
                "            },\n" +
                "            columnDefs: [CONSTANT.BUTTON.CHECKBOXS],\n" +
                "            drawCallback: function (settings) {\n" +
                "                $(\":checkbox[name='check-all']\").prop(\"checked\", false);\n" +
                "            }\n" +
                "        }));\n" +
                "\n" +
                "        $(\"#search\").on(\"click\", function () {\n" +
                "            tables.draw();\n" +
                "        });\n" +
                "\n" +
                "        $(\"#export\").click(function () {\n" +
                "            var formData = $(\"#queryForm\").serialize();\n" +
                "            location.href = '/admin/module/exportExcel?' + formData;\n" +
                "        });\n" +
                "\n" +
                "        $(\"#btn-del\").click(function () {\n" +
                "            var arrItemId = [];\n" +
                "            $(\"tbody :checkbox:checked\", $table).each(function (i) {\n" +
                "                var item = tables.row($(this).closest('tr')).data();\n" +
                "                arrItemId.push(item.id);\n" +
                "                $(this).closest('tr').remove();\n" +
                "            });\n" +
                "            console.log(arrItemId);\n" +
                "        });\n" +
                "\n" +
                "        $table.on(\"change\", \":checkbox\", function () {\n" +
                "            if ($(this).is(\"[name='check-all']\")) {\n" +
                "                $(\":checkbox\", $table).prop(\"checked\", $(this).prop(\"checked\"));\n" +
                "            } else {\n" +
                "                var checkbox = $(\"tbody :checkbox\", $table);\n" +
                "                $(\":checkbox[name='check-all']\", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);\n" +
                "            }\n" +
                "        }).on(\"click\", \".btn-edit\", function () {\n" +
                "            var item = tables.row($(this).closest('tr')).data();\n" +
                "        }).on(\"click\", \".btn-enable\", function () {\n" +
                "            var item = tables.row($(this).closest('tr')).data();\n" +
                "            layerConfirm('确定要...吗', \"/admin/module/moduleEnable?id=\" + item.id, function () {\n" +
                "                reload();\n" +
                "            });\n" +
                "        });\n" +
                "\n" +
                "        datePicker('#startDate,#endDate', \"yyyy-mm-dd\", 2);\n" +
                "    });\n" +
                "\n" +
                "    function reload() {\n" +
                "        tables.draw('page');\n" +
                "    }\n" +
                "\n" +
                "    function dealData(data) {\n" +
                "        console.log(\"选择的数量为：\" + data.length);\n" +
                "        console.log(data);\n" +
                "    }\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>");
        bw2.flush();
        bw2.close();
    }

    /**
     * 生成实体类
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildEntityBean(List<String> columns, List<String> types, List<String> comments, String tableComment)
            throws IOException {
        File folder = new File(bean_path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        int size = columns.size();

        File beanFile = new File(bean_path, beanName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
        bw.write("package " + bean_package + ";\r\n");
        bw.newLine();
        // 基类
        bw.write("import " + base_vo + "");
        bw.newLine();
        bw.newLine();
        // 导入包
        bw.write("import java.io.Serializable;");
        if (checkDate(size, types)) {
            bw.newLine();
            bw.write("import java.util.Date;");
        }
        bw = buildClassComment(bw, tableComment);
        bw.newLine();
        // 类
        bw.write("public class " + beanName + " extends BaseVo implements Serializable {");
        bw.newLine();
        bw.newLine();
        bw.write("\tprivate static final long serialVersionUID = 1L;");
        bw.newLine();
        bw.newLine();

        boolean idInt = false;
        for (int i = 0; i < size; i++) { // 生成字段
            bw.write("\t/** " + comments.get(i) + " */");
            bw.newLine();
            bw.write("\tprivate " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
            bw.newLine();
            bw.newLine();
            if ("id".equals(processField(columns.get(i)))) {
                if ("Integer".equals(processType(types.get(i)))) {
                    idInt = true;
                }
            }
        }
        //生成构造方法
        bw.write("\tpublic " + beanName + "() {");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();
        bw.write("\tpublic " + beanName + "(" + (idInt ? "Integer" : "Long") + " id) {");
        bw.newLine();
        bw.write("\t\tsuper();");
        bw.newLine();
        bw.write("\t\tthis.id = id;");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        // 生成get和 set方法
        String tempField = null;
        String _tempField = null;
        String tempType = null;
        for (int i = 0; i < size; i++) {
            tempType = processType(types.get(i));
            _tempField = processField(columns.get(i));
            tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
            bw.newLine();
            bw.write("\tpublic " + tempType + " get" + tempField + "() {");
            bw.newLine();
            bw.write("\t\treturn this." + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();
            bw.write("\tpublic void set" + tempField + "(" + tempType + " " + _tempField + ") {");
            bw.newLine();
            bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
        }
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    /**
     * 构建Mapper接口文件
     *
     * @throws IOException
     */
    private void buildMapper() throws IOException {
        File folder = new File(mapper_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mapperFile = new File(mapper_path, mapperName + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
        bw.write("package " + mapper_package + ";");
        bw.newLine();
        bw.newLine();
        bw.write("import java.util.List;\r\nimport " + bean_package + "." + beanName + ";");
        bw.newLine();
        // 自定义查询封装类
        bw.write("import com.jf.entity.BaseVo;");
        bw = buildClassComment(bw, mapperName + " Interface");
        bw.newLine();
        bw.write("public interface " + mapperName + " {");
        bw.newLine();

        // ----------定义Mapper中的方法Begin----------
        bw.newLine();
        //bw.write("\t/**\r\n\t * \r\n\t * @param baseVo\r\n\t * @return\r\n\t */\r\n");
        bw.write("\tList<" + beanName + "> findByCondition(BaseVo baseVo);");
        bw.newLine();
        bw.newLine();
        //bw.write("\t/**\r\n\t * \r\n\t * @param baseVo\r\n\t * @return\r\n\t */\r\n");
        bw.write("\tint findCountByCondition(BaseVo baseVo);");
        bw.newLine();
        bw.newLine();
        bw.write("\t" + beanName + " findById(Long id);");
        bw.newLine();
        bw.newLine();
        bw.write("\t" + beanName + " findSimpleById(Long id);");
        bw.newLine();
        bw.newLine();
        bw.write("\tObject findFieldById(@Param(\"id\") Long id, @Param(\"field\") String field);");
        bw.newLine();
        bw.newLine();
        //bw.write("\t/**\r\n\t * \r\n\t * @param bean\r\n\t */\r\n");
        bw.write("\t" + "int insert(" + beanName + " bean);");
        bw.newLine();
        bw.newLine();
        //bw.write("\t/**\r\n\t * \r\n\t * @param list\r\n\t */\r\n");
        bw.write("\t" + "int insertBatch(List<" + beanName + "> list);");
        bw.newLine();
        bw.newLine();
        //bw.write("\t/**\r\n\t * \r\n\t * @param bean\r\n\t */\r\n");
        bw.write("\t" + "int update(" + beanName + " bean);");
        bw.newLine();
        bw.newLine();
        //bw.write("\t/**\r\n\t * \r\n\t * @param id\r\n\t */\r\n");
        bw.write("\t" + "int delete(Long id);");
        bw.newLine();
        bw.newLine();
        bw.write("\t" + "int deleteBatch(@Param(\"ids\") Long[] ids);");
        bw.newLine();

        // ----------定义Mapper中的方法End----------
        bw.newLine();
        bw.write("}");
        bw.flush();
        bw.close();
    }

    /**
     * 构建实体类映射XML文件
     *
     * @param columns
     * @param types
     * @param comments
     * @throws IOException
     */
    private void buildMapperXml(List<String> columns, List<String> types, List<String> comments) throws IOException {
        File folder = new File(xml_path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File mapperXmlFile = new File(xml_path, mapperName + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        bw.newLine();
        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
        bw.newLine();
        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        bw.newLine();
        bw.newLine();
        // 命名空间
        bw.write("<mapper namespace=\"" + mapper_package + "." + mapperName + "\">");
        bw.newLine();
        bw.newLine();

        // 下面开始写SqlMapper中的方法
        // 构建通用方法，其它可根据需要新增或改写
        buildSQL(bw, columns, types);

        bw.write("</mapper>");
        bw.flush();
        bw.close();
    }

    private void buildSQL(BufferedWriter bw, List<String> columns, List<String> types) throws IOException {
        int size = columns.size();

        bw.write("\t<sql id=\"baseCondition\">");
        bw.newLine();
        bw.write("\t\t<where>");
        bw.newLine();
        bw.write("\t\t\t<include refid=\"COMMON.DATE\"><property name=\"column\" value=\"create_time\"/></include>");
        bw.newLine();
        bw.write("\t\t</where>");
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();

        bw.write("\t<sql id=\"simpleColumn\">");
        bw.newLine();
        bw.write("\t\tid\r\n");
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();

        bw.write("\t<sql id=\"allColumn\">");
        bw.newLine();
        bw.write("\t\t");
        String allColumn = "";
        for (int i = 0; i < size; i++) {
            allColumn += columns.get(i);
            if (i < size - 1) {
                allColumn += ",\r\n\t\t";
            }
        }
        bw.write(allColumn);
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();

        bw.write("\t<resultMap type=\"" + processResultMapId(beanName) + "\" id=\"baseResultMap\">");
        bw.newLine();
        bw.write("\t\t<id property=\"id\" column=\"id\" />");
        bw.newLine();
        for (int i = 0; i < size; i++) {
            if (!"id".equals(columns.get(i))) {
                bw.write("\t\t<result property=\"" + processField(columns.get(i)) + "\" column=\"" + columns.get(i) +
                        "\" />");
                bw.newLine();
            }
        }
        bw.write("\t</resultMap>");
        bw.newLine();
        bw.newLine();

        // 条件查询-findBeanByCondition
        bw.write("\t<select id=\"findByCondition\" resultMap=\"baseResultMap\" parameterType=\"baseVo\">");
        bw.newLine();
        bw.write("\t\tSELECT <include refid=\"allColumn\"/> \r\n\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\t<include refid=\"baseCondition\"/>");
        bw.newLine();
        bw.write("\t\t<include refid=\"COMMON.ORDER\"/>");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();

        // 条件查询总数-findBeanCountByCondition
        bw.write("\t<select id=\"findCountByCondition\" resultType=\"int\" parameterType=\"baseVo\">");
        bw.newLine();
        bw.write("\t\tSELECT COUNT(1) \r\n\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\t<include refid=\"baseCondition\"/>");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();

        // 通过id查询
        bw.write("\t<select id=\"findById\" resultMap=\"baseResultMap\" parameterType=\"long\">");
        bw.newLine();
        bw.write("\t\tSELECT <include refid=\"allColumn\"/> \r\n\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE id = #{id}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();

        // 通过id查询-simple
        bw.write("\t<select id=\"findSimpleById\" resultMap=\"baseResultMap\" parameterType=\"long\">");
        bw.newLine();
        bw.write("\t\tSELECT <include refid=\"simpleColumn\"/> \r\n\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE id = #{id}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();

        // findFieldById
        bw.write("\t<select id=\"findFieldById\" resultType=\"object\">");
        bw.newLine();
        bw.write("\t\tSELECT ${field} \r\n\t\tFROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE id = #{id}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();

        // 添加insert方法-insertBean
        bw.write("\t<insert id=\"insert\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("\t\tINSERT INTO " + tableName);
        bw.write(" (\r\n");
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                bw.write("\t\t\t" + columns.get(i) + ",\r\n");
            } else {
                bw.write("\t\t\t" + columns.get(i) + "\r\n");
            }
        }
        bw.write("\t\t)");
        bw.newLine();
        bw.write("\t\tVALUE ");
        bw.write("(\r\n");
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                bw.write("\t\t\t#{" + processField(columns.get(i)) + "},\r\n");
            } else {
                bw.write("\t\t\t#{" + processField(columns.get(i)) + "}\r\n");
            }
        }
        bw.write("\t\t)");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();

        // 批量插入-insertBatch
        bw.write("\t<insert id=\"insertBatch\" parameterType=\"list\">");
        bw.newLine();
        bw.write("\t\tINSERT INTO " + tableName);
        bw.write(" (\r\n");
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                bw.write("\t\t\t" + columns.get(i) + ",\r\n");
            } else {
                bw.write("\t\t\t" + columns.get(i) + "\r\n");
            }
        }
        bw.write("\t\t)");
        bw.newLine();
        bw.write("\t\tVALUES ");
        bw.write("\r\n");
        bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">\r\n");
        bw.write("\t\t(");
        bw.newLine();
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                bw.write("\t\t\t#{item." + processField(columns.get(i)) + "},\r\n");
            } else {
                bw.write("\t\t\t#{item." + processField(columns.get(i)) + "}\r\n");
            }
        }
        bw.write("\t\t)");
        bw.newLine();
        bw.write("\t\t</foreach>");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();

        // 修改update方法-updateBean
        bw.write("\t<update id=\"update\" parameterType=\"" + processResultMapId(beanName) + "\">");
        bw.newLine();
        bw.write("\t\tUPDATE " + tableName);
        bw.newLine();
        bw.write("\t\t<set>");
        bw.newLine();

        String tempField = null;
        for (int i = 1; i < size; i++) {
            tempField = processField(columns.get(i));
            if ("String".equals(processType(types.get(i)))) {
                bw.write("\t\t\t<if test=\"" + tempField + " != null and " + tempField + " != ''\">");
            } else {
                bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
            }
            bw.write("" + columns.get(i) + " = #{" + tempField + "},");
            bw.write("</if>");
            bw.newLine();
        }
        bw.write("\t\t</set>");
        bw.newLine();
        bw.write("\t\tWHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();

        // 删除-deleteBean
        bw.write("\t<delete id=\"delete\" parameterType=\"long\">");
        bw.newLine();
        bw.write("\t\tDELETE FROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();

        // 批量删除-deleteBatch
        bw.write("\t<delete id=\"deleteBatch\">");
        bw.newLine();
        bw.write("\t\tDELETE FROM " + tableName);
        bw.newLine();
        bw.write("\t\tWHERE id IN <foreach item=\"id\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> #{id} </foreach>");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
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

    /**
     * 构建主方法
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @author rick
     * @date 2016年8月10日 上午9:00:31
     */
    public void generate() throws ClassNotFoundException, SQLException, IOException {
        init();
        String prefix = "show full fields from ";
        List<String> columns = null;
        List<String> types = null;
        List<String> comments = null;
        PreparedStatement pstate = null;
        List<String> tables = getTables();
        Map<String, String> tableComments = getTableComment();
        for (String table : tables) {
            columns = new ArrayList<String>();
            types = new ArrayList<String>();
            comments = new ArrayList<String>();
            pstate = conn.prepareStatement(prefix + table);
            ResultSet results = pstate.executeQuery();
            while (results.next()) {
                columns.add(results.getString("FIELD"));
                types.add(results.getString("TYPE"));
                comments.add(results.getString("COMMENT"));
            }
            tableName = table;
            processTable(table);
            String tableComment = tableComments.get(tableName);

            buildEntityBean(columns, types, comments, tableComment);
            buildForm(columns, types, comments, tableComment);
            buildMapper();
            buildMapperXml(columns, types, comments);
        }
        System.out.println("数据库表共" + tables.size() + "个");
        conn.close();
    }

    public static void main(String[] args) {
        System.out.println("开始执行......");
        System.out.println("开始清理......");
        deleteFile(new File(bean_path));
        deleteFile(new File(mapper_path));
        deleteFile(new File(xml_path));
        deleteFile(new File(html_path));

        long start = System.currentTimeMillis();
        try {
            new GenerateBeansAndMybatisUtil().generate();
            System.out.println("执行完成，总用时 : " + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            System.out.println("执行出现问题，请检查");
            e.printStackTrace();
        }
    }

    private static void deleteFile(File parent) {
        if (parent.exists()) {
            File[] files = parent.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }
}