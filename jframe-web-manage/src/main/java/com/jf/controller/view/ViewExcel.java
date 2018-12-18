package com.jf.controller.view;

import com.fasterxml.jackson.databind.util.ClassUtil;
import com.jf.annotation.excel.Excel;
import com.jf.annotation.excel.Fields;
import com.jf.annotation.excel.TypeValue;
import com.jf.commons.LogManager;
import com.jf.poi.AbstractCellRender;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 生成Excel视图
 * <p>1.map{name:表格名称,list:List类型}</p>
 * <p>2.SpringMvc用法:return new ModelAndView(new ViewExcel<UserModel>(), model);</p>
 *
 * @author rick
 * @version 2.0
 */
public class ViewExcel<T> extends AbstractXlsView {

    private final String NULL_VALUE = "--";

    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        return new SXSSFWorkbook();
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // List<T>类型
        List<T> list = (List<T>) map.get("list");
        if (list.isEmpty()) {
            return;
        }

        T t = list.get(0);
        Excel excel = t.getClass().getAnnotation(Excel.class);
        if (excel == null || excel.name() == null) {
            throw new RuntimeException("请指定Excel表名");
        }

        // 表名
        String excelName = new StringBuilder(excel.name())
                .append(new DateTime(System.currentTimeMillis()).toString("yyyy-MM-dd-HH-mm-ss"))
                .append(".xlsx").toString(); // 文件后缀为xlsx(excel 2010)
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));

        // 创建sheet
        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(excelName);
        // 产生Excel表头
        Row header = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(font);

        Field[] fs = t.getClass().getDeclaredFields(); // 所有字段
        int cell = 0; // 列序号，从0开始
        for (int i = 0; i < fs.length; i++) {
            Fields field = fs[i].getAnnotation(Fields.class); // 获取Fields注解信息
            if (field != null) {
                Cell c = header.createCell(cell);
                c.setCellValue(field.value());
                c.setCellStyle(style); // 样式
                cell++;
            }
        }

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int total = cell; // 总列
        int line = 1; // 行序号，第二行开始
        for (T model : list) {
            Row row = sheet.createRow(line++); // 创建行
            cell = 0; // 行列
            for (int i = 0; i < fs.length; i++) {
                Fields field = fs[i].getAnnotation(Fields.class); // 获取Fields注解信息
                if (field != null) {
                    String fieldName = fs[i].getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); // 属性的get方法，必须以get开头，is不支持
                    Method getMethod = t.getClass().getMethod(getMethodName, new Class[]{});
                    Object obj = getMethod.invoke(model, new Object[]{}); // 执行get方法
                    String val = String.valueOf(obj);
                    if ("null".equals(val) || "".equals(val)) {
                        row.createCell(cell).setCellValue(NULL_VALUE); // 默认值
                    } else {
                        // 转换器(优先)
                        Class<? extends AbstractCellRender> render = field.render();
                        if (render != AbstractCellRender.None.class) {
                            AbstractCellRender r = ClassUtil.createInstance(render, true);
                            String result = r.render(val);
                            row.createCell(cell).setCellValue(result);
                        } else {
                            switch (field.type()) {
                                // 其他类型暂定
                                case ENUM: // 枚举
                                    TypeValue[] values = field.typeValues();
                                    for (int j = 0; j < values.length; j++) {
                                        if (values[j].value().equals(val)) {
                                            row.createCell(cell).setCellValue(values[j].name());
                                        }
                                    }
                                    break;
                                case BOOLEAN:
                                    if ("1".equals(val)) {
                                        row.createCell(cell).setCellValue("是");
                                    } else if ("0".equals(val)) {
                                        row.createCell(cell).setCellValue("否");
                                    } else {
                                        row.createCell(cell).setCellValue(NULL_VALUE);
                                    }
                                    break;
                                default: // 字符
                                    row.createCell(cell).setCellValue(val);
                                    break;
                            }
                        }
                    }
                    cell++;
                }
            }
        }

        LogManager.info(excelName + " 已导出，总计" + (line - 1) + "行，" + total + "列", ViewExcel.class);
        // 自动列宽
        sheet.trackAllColumnsForAutoSizing();
        for (int i = 0; i < total; i++) {
            sheet.autoSizeColumn(i);
        }
    }

}