package com.jf.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 写入Excel SXSS
 * [测试版]
 *
 * @author rick
 */
public class ExcelWriterSXSS {

    /**
     * 写入标题
     *
     * @param sheet
     * @param rowNum 第几行的行号
     * @param values key:第几列的列号  value:值
     */
    public static void genSheetHead(Sheet sheet, int rowNum, Map<Integer, Object> values) {

        Row row = sheet.createRow(rowNum);
        for (Integer cellNum : values.keySet()) {
            Cell cell = row.createCell(cellNum);
            Object value = values.get(cellNum);
            generateValue(value, cell);
        }
    }

    /**
     * @param row
     * @param cellNum 第几列的列号
     * @param value   值
     */
    public static void createCell(Row row, int cellNum, Object value) {
        Cell cell = row.createCell(cellNum);
        generateValue(value, cell);
    }

    private static void generateValue(Object value, Cell cell) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        }
    }

    public static void main(String[] args) throws Exception {

        // 输入模板文件
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 100);

        // 导出文件
        File file = new File("/Users/xujunfei/Downloads/output.xlsx");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            Sheet sheet = workbook.getSheet("sheet" + (i + 1));
            if (sheet == null) {
                sheet = workbook.createSheet("sheet" + (i + 1));
            }

            // 生成标题
            Map<Integer, Object> firstTitles = new HashMap<>();
            firstTitles.put(0, "部门:");
            firstTitles.put(1, "test12221");
            firstTitles.put(7, "时间:");
            firstTitles.put(8, "2017-09-11");
            genSheetHead(sheet, 0, firstTitles);

            Map<Integer, Object> twoTitles = new HashMap<>();
            twoTitles.put(0, "工号：");
            twoTitles.put(1, "test12221");
            twoTitles.put(2, "姓名:");
            twoTitles.put(3, "aaaa");
            genSheetHead(sheet, 1, twoTitles);

            for (int rownum = 2; rownum < 100000; rownum++) {
                Row row = sheet.createRow(rownum);
                int k = -1;
                createCell(row, ++k, "第 " + rownum + " 行");
                createCell(row, ++k, 34343.123456789);
                createCell(row, ++k, "23.67%");
                createCell(row, ++k, "12:12:23");
                createCell(row, ++k, "2014-10-<11 12:12:23");
                createCell(row, ++k, "true");
                createCell(row, ++k, "false");
                createCell(row, ++k, "fdsa");
                createCell(row, ++k, "123");
                createCell(row, ++k, "321");
                createCell(row, ++k, "3213");
                createCell(row, ++k, "321");
                createCell(row, ++k, "321");
                createCell(row, ++k, "43432");
                createCell(row, ++k, "54");
                createCell(row, ++k, "fal45se");
                createCell(row, ++k, "fal6se");
                createCell(row, ++k, "fal64321se");
                createCell(row, ++k, "fal43126se");
                createCell(row, ++k, "432432");
                createCell(row, ++k, "432432");
                createCell(row, ++k, "r54");
                createCell(row, ++k, "543");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1a");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
                createCell(row, ++k, "few1");
            }

        }
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);

        System.out.println((System.currentTimeMillis() - start));
        out.close();
    }

}
