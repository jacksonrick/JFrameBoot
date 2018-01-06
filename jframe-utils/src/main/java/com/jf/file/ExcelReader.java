package com.jf.file;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对于表格后缀为 xlsx的Excel表格进行解析读取的工具类
 */
public class ExcelReader {

    /**
     * Excel 对象
     */
    private XSSFWorkbook workbook;

    /**
     * 当前操作 Sheet 对象
     */
    private XSSFSheet sheet;

    /**
     * <b>构造方法</b>
     * <br/>
     *
     * @param excelURI Excel路径
     */
    public ExcelReader(String excelURI) {
        if (excelURI == null || excelURI.trim().length() <= 0) {
            System.out.println("传入路径为空");
        }

        //文件输入流
        InputStream inputStream = null;
        try {
            //获取文件输入流
            inputStream = new FileInputStream(excelURI);

            //获取 Excel 对象
            workbook = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * <b>构造方法</b>
     * <br/>
     *
     * @param inputStream Excel文件输入流
     */
    public ExcelReader(InputStream inputStream) {
        //获取 Excel 对象
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.out.println("读取文件失败");
        }
    }

    public Object readPoint(int rowNum, int cellNum) {
        //有数据信息总行数
        int totalRow = getTotalRow();

        //如果要读取的行大于有数据行，则返回空字符串
        if (rowNum >= totalRow) {
            return "";
        }

        //获取行信息
        XSSFRow rows = this.getSheet().getRow(rowNum);

        //返回列信息
        return getValue(rows.getCell(cellNum));
    }

    public Object[] readCell(int cellNum) {
        //获取总行数
        int totalRow = getTotalRow();

        //生成列信息集合
        Object[] result = new Object[totalRow];

        //遍历行号，获取每行的列信息
        for (int i = 0; i < totalRow; i++) {
            result[i] = readPoint(i, cellNum);
        }
        return result;
    }

    public Object[] readRow(int rowNum) {
        //获取总行数
        int totalRow = getTotalRow();

        //如果要查询行信息大于
        if (rowNum >= totalRow) {
            return new String[0];
        }

        XSSFRow row = this.getSheet().getRow(rowNum);

        //获取第一行的列数
        int cellNum = this.getSheet().getRow(rowNum).getPhysicalNumberOfCells();

        //返回行信息
        Object[] result = new Object[cellNum];

        //遍历列信息
        for (int i = 0; i < cellNum; i++) {
            //当前行的当前列信息
            result[i] = getValue(row.getCell(i));
        }
        return result;
    }

    public List<Object[]> readPage(int startRowIndex, int endRowIndex) {
        if (startRowIndex < 0) {
            startRowIndex = 0;
        }

        int totalRow = this.getTotalRow();

        if (endRowIndex < 0 || endRowIndex > totalRow) {
            endRowIndex = totalRow;
        }

        //返回用的值
        List<Object[]> result = new ArrayList<Object[]>();

        //每行信息
        Object[] cells = null;

        //便利各行
        for (int i = startRowIndex; i < endRowIndex; i++) {

            //获取当前行
            XSSFRow row = this.getSheet().getRow(i);

            //如果当前行为空则返回空数组
            if (row == null) {
                cells = new Object[0];
                result.add(cells);
                continue;
            }

            //当前行有数据的列个数
            int cellNum = row.getPhysicalNumberOfCells();

            //生成行数组
            cells = new Object[cellNum];

            //便利当前行
            for (short j = 0; j < cellNum; j++) {
                //获取当前行的当前列
                cells[j] = getValue(row.getCell(j));
            }
            result.add(cells);
        }
        return result;
    }

    /**
     * <b>方法说明：</b>
     * <ul>
     * 获取Excel中某一行某一列的值
     * </ul>
     *
     * @param cell
     * @return Object
     */
    private Object getValue(XSSFCell cell) {
        if (null == cell) {
            return "";
        }

        System.out.println(cell.getCellType());
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_BLANK://空值
                return "";
            case HSSFCell.CELL_TYPE_ERROR:// 故障
                return "";
            case HSSFCell.CELL_TYPE_STRING://字符串类型
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔类型
                return cell.getBooleanCellValue();
            case XSSFCell.CELL_TYPE_NUMERIC://数值型
                //如果是日期，则转换成日期返回
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getRawValue();
            // 公式类型
            case HSSFCell.CELL_TYPE_FORMULA:
                //读公式计算值
                try {
                    String value = String.valueOf(cell.getNumericCellValue());
                    //解决double数值过大出现科学计数法
                    if (value.indexOf("E") != -1) {
                        BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                        bd.setScale(2);
                        value = bd.toString();
                    }
                    if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                        value = cell.getStringCellValue().toString();
                    }
                } catch (Exception e) {
                    return cell.getRawValue();
                }
            default:
                return cell.getRawValue();
        }

    }

    /**
     * <b>方法说明：</b>
     * <ul>
     * 获取当前操作的 Sheet<br/>
     * 如果没有设置当前操作 Sheet，则默认当前操作 Sheet 为第一个
     * </ul>
     *
     * @return XSSFSheet
     */
    private XSSFSheet getSheet() {
        if (sheet == null) {
            synchronized (this) {
                if (sheet == null) {
                    sheet = this.workbook.getSheetAt(0);
                }
            }
        }
        return sheet;
    }

    public void setSheetName(String sheetName) {
        XSSFSheet getSheet = this.workbook.getSheet(sheetName);
        if (getSheet == null) {
            throw new IllegalArgumentException("将要获取的页面[" + sheetName + "]不存在");
        }
        sheet = getSheet;
    }

    public void setSheetIndex(int index) {
        XSSFSheet getSheet = this.workbook.getSheetAt(index);
        if (getSheet == null) {
            throw new IllegalArgumentException("将要获取的页面下标[" + index + "]不存在");
        }
        sheet = getSheet;
    }

    public int getTotalRow() {
        return this.getSheet().getPhysicalNumberOfRows();
    }

}

