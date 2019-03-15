package com.jf.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * 写入Excel [SXSS]
 *
 * @author rick
 */
public class ExcelWriterSXSS {

    // 在内存中保持100行，超过100行将被刷新到磁盘
    private SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
    private SXSSFSheet sheet = null;

    public ExcelWriterSXSS() {
        sheet = workbook.createSheet("sheet");
    }

    /**
     * 写入数据
     *
     * @param data
     * @throws Exception
     */
    public void write(List<String> data) throws Exception {
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i);
            String[] strs = String.valueOf(data.get(i)).split(",");
            for (int j = 0; j < strs.length; j++) {
                ExcelWriterSXSS.createCell(row, j, strs[j]);
            }
        }

        // 导出文件
        File file = new File("/Users/xujunfei/Downloads/output1.xlsx");
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
        workbook.dispose();
    }

    /**
     * 写入标题
     *
     * @param sheet
     * @param rowNum 第几行的行号
     * @param values key:第几列的列号  value:值
     */
    public static void genSheetHead(Sheet sheet, int rowNum, Map<Integer, String> values) {
        Row row = sheet.createRow(rowNum);
        for (Integer cellNum : values.keySet()) {
            Cell cell = row.createCell(cellNum);
            String value = values.get(cellNum);
            cell.setCellValue(value);
        }
    }

    /**
     * @param row
     * @param cellNum 第几列的列号
     * @param value   值
     */
    public static void createCell(Row row, int cellNum, String value) {
        Cell cell = row.createCell(cellNum);
        cell.setCellValue(value);
    }

}
