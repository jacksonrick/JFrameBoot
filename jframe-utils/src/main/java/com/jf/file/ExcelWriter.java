package com.jf.file;

import com.jf.obj.ObjectUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * 写入Excel的工具类
 */
public class ExcelWriter {

    /**
     * Excel 对象类
     */
    private XSSFWorkbook workbook;

    /**
     * 当前待处理的页面
     */
    private XSSFSheet sheet;

    public ExcelWriter() {
        this.workbook = new XSSFWorkbook();
    }

    /**
     * <ul>
     * 获取当前操作的页面<br/>
     * 当页面为空时，新建立一个页面
     * </ul>
     *
     * @return
     */
    private XSSFSheet getSheet() {
        if (sheet == null) {
            synchronized (this) {
                if (sheet == null) {
                    sheet = this.workbook.createSheet();
                }
            }
        }
        return sheet;
    }

    public void setSheetName(String sheetName) {
        XSSFSheet getSheet = this.workbook.getSheet(sheetName);
        if (getSheet == null) {
            getSheet = this.workbook.createSheet(sheetName);
        }
        sheet = getSheet;
    }

    public void setSheetIndex(int index) {

        XSSFSheet getSheet = this.workbook.getSheetAt(index);
        if (getSheet == null) {
            getSheet = this.workbook.getSheetAt(index - 1);
            if (getSheet == null) {
                throw new IllegalArgumentException("当前没有[" + index + "]页码");
            }
            getSheet = this.workbook.createSheet();
        }
        sheet = getSheet;
    }

    public int getTotalRow() {
        return this.getSheet().getPhysicalNumberOfRows();
    }

    public void writeRow(List<Object[]> list) {
        if (list == null || list.size() <= 0) {
            return;
        }

        //当前总行数
        int totalRow = this.getTotalRow();

        //遍历插入信息
        for (int i = 0; i < list.size(); i++) {
            modifyExcelRow(list.get(i), totalRow + i);
        }
    }

    public void writeRow(Object[] cellVaueArray) {
        modifyExcelRow(cellVaueArray, this.getTotalRow());
    }

    public void modifyExcelRow(Object[] cellVaueArray, int rowNumber) {
        //创建新行
        XSSFRow row = this.getSheet().createRow(rowNumber);

        //如果为空，则本行为空
        if (cellVaueArray == null || cellVaueArray.length <= 0) {
            return;
        }

        for (int j = 0; j < cellVaueArray.length; j++) {
            if (cellVaueArray[j] == null) {
                continue;
            }

            //定义单元格
            XSSFCell cell = row.createCell(j);

            Class<?> clazz = cellVaueArray[j].getClass();

            if (ObjectUtil.isIntergrated(clazz, String.class)) {//字符串
                cell.setCellValue((String) cellVaueArray[j]);
            } else if (ObjectUtil.isIntergrated(clazz, long.class)
                    || ObjectUtil.isIntergrated(clazz, Long.class)) {//长整形
                cell.setCellValue((Long) cellVaueArray[j]);
            } else if (ObjectUtil.isIntergrated(clazz, int.class)
                    || ObjectUtil.isIntergrated(clazz, Integer.class)
                    || ObjectUtil.isIntergrated(clazz, short.class)
                    || ObjectUtil.isIntergrated(clazz, Short.class)) {//整形
                cell.setCellValue((Integer) cellVaueArray[j]);
            } else if (ObjectUtil.isIntergrated(clazz, double.class)
                    || ObjectUtil.isIntergrated(clazz, Double.class)
                    || ObjectUtil.isIntergrated(clazz, float.class)
                    || ObjectUtil.isIntergrated(clazz, Float.class)) {//浮点型
                cell.setCellValue((Double) cellVaueArray[j]);
            } else if (ObjectUtil.isIntergrated(clazz, Date.class)) {
                cell.setCellValue((Date) cellVaueArray[j]);
            } else {
                cell.setCellValue(cellVaueArray[j].toString());
            }
        }
    }

    public void outputExcelToFile(String excelURI) throws IOException {
        //写入流
        FileOutputStream out = null;

        try {
            //初始化输出流
            out = new FileOutputStream(excelURI);

            outputExcel(out);
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                } catch (Exception e) {
                }
                try {
                    out.close();
                } catch (Exception e) {
                }
                out = null;
            }
        }
    }

    public void outputExcel(OutputStream outputStream) throws IOException {
        this.workbook.write(outputStream);
    }

}

