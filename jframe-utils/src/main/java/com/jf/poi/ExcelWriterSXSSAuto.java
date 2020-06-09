package com.jf.poi;

import com.fasterxml.jackson.databind.util.ClassUtil;
import com.jf.annotation.excel.Excel;
import com.jf.annotation.excel.Fields;
import com.jf.annotation.excel.TypeValue;
import com.jf.exception.SysException;
import com.jf.poi.render.AbstractCellRender;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Excel大量数据导出 注解式
 * User: xujunfei
 * Date: 2019-03-11
 * Time: 10:21
 */
public class ExcelWriterSXSSAuto<T> {

    private List<T> datas = new ArrayList<>();
    public String excelName;
    private int rowIndex = 0; // 行索引
    private int totalRow = 0; // 总列
    private Class<T> clz;
    private Field[] fs;

    private final String NULL_VALUE = "--";

    // 在内存中保持1000行，超过1000行将被刷新到磁盘
    private SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
    private SXSSFSheet sheet = null;

    public ExcelWriterSXSSAuto(Class<T> clazz) {
        this.clz = clazz;
        Excel excel = clz.getAnnotation(Excel.class);
        if (excel == null || excel.name() == null) {
            throw new RuntimeException("请指定Excel表名");
        }
        // 表名
        excelName = new StringBuilder(excel.name())
                .append(new DateTime(System.currentTimeMillis()).toString("yyyy-MM-dd-HH-mm-ss"))
                .append(".xlsx").toString();
        // 创建sheet
        sheet = workbook.createSheet("sheet1");
        // sheet.setRandomAccessWindowSize(-1);
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

        fs = clz.getDeclaredFields(); // 所有字段
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
        totalRow = cell;
    }

    /**
     * 写入数据
     *
     * @param data
     * @param path 输出目录
     * @return
     * @throws Exception
     */
    public String write(List<T> data, String path) throws Exception {
        writePage(data);
        return export(path);
    }

    /**
     * 写入分页数据
     *
     * @param data
     */
    public void writePage(List<T> data) throws Exception {
        int line = rowIndex + 1; // 行序号，第二行开始
        for (T model : data) {
            Row row = sheet.createRow(line++); // 创建行
            int cell = 0; // 列序号
            for (int i = 0; i < fs.length; i++) {
                Fields field = fs[i].getAnnotation(Fields.class); // 获取Fields注解信息
                if (field != null) {
                    String fieldName = fs[i].getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); // 属性的get方法，必须以get开头，is不支持
                    Method getMethod = clz.getMethod(getMethodName, new Class[]{});
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

        rowIndex += data.size();
    }

    /**
     * 输出文件
     *
     * @param path 路径需要以 / 结尾
     */
    public String export(String path) {
        // 自动列宽
        sheet.trackAllColumnsForAutoSizing();
        // 输出到文件
        File file = new File(path + excelName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new SysException(e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new SysException(e.getMessage(), e);
            }
            workbook.dispose();
        }
    }

}
