package com.jf.poi;

import com.jf.annotation.excel.Fields;
import com.jf.date.DateUtil;
import com.jf.exception.SysException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 注解式导入Excel
 * <p>支持excel2007以上版本，适合万级以下的数据</p>
 *
 * @author rick
 * @version 1.2
 */
public class ExcelReaderXSSAuto {

    /*
        ExcelReaderXSSAuto xss = new ExcelReaderXSSAuto();
        List<Wage> list = xss.read(is, Wage.class);
    */

    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private XSSFRow row;

    /**
     * 读取Excel
     *
     * @param is
     * @param clz
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> read(InputStream is, Class<T> clz) throws Exception {
        // 延迟解析比率
        ZipSecureFile.setMinInflateRatio(-1.0d);
        wb = new XSSFWorkbook(is);
        // 获取第一个sheet
        sheet = wb.getSheetAt(0);
        // 读取总行数
        int rowNum = sheet.getLastRowNum();
        // 第一行-标题
        XSSFRow row0 = sheet.getRow(0);
        // 读取总列数
        int colNum = row0.getPhysicalNumberOfCells();

        List<T> list = new ArrayList<>();
        Field[] fs = clz.getDeclaredFields(); // 所有字段

        for (int j = 1; j <= rowNum; j++) { // 读行-数据从第二行起
            T entity = clz.newInstance(); // 实例化实体
            row = sheet.getRow(j); // 获取行记录
            if (isRowEmpty(row, colNum)) { // 判读空行
                continue;
            }
            int k = 0;
            while (k < colNum) { // 读列
                String name = row0.getCell(k).getStringCellValue(); // 读取列名
                for (int i = 0; i < fs.length; i++) {
                    Fields field = fs[i].getAnnotation(Fields.class); // 获取Fields注解信息
                    if (field != null) {
                        String colName = field.value(); // 指定对应列名
                        if (colName.equals(name)) {
                            String fieldName = fs[i].getName(); // 字段名
                            String fieldType = fs[i].getType().getSimpleName(); // 实体类字段类型
                            Object val = getCellFormatValue(row.getCell(k));
                            setEntityValue(fieldName, val, entity, j, k);
                        }
                    }
                }
                k++;
            }
            list.add(entity);
        }

        return list;
    }

    /**
     * @param field
     * @param value
     * @param entity
     * @param <T>
     * @throws Exception
     */
    private <T> void setEntityValue(String field, Object value, T entity, int j, int k) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field, entity.getClass());
            String fieldType = pd.getPropertyType().getSimpleName();
            Object finalVal = value;
            // 枚举可能出现的情况
            switch (fieldType) { // 判断实体类字段类型，再根据其转换
                case "Integer":
                    if (value instanceof Double) {
                        finalVal = new Double(String.valueOf(value)).intValue();
                    } else if (value instanceof String) {
                        finalVal = Integer.valueOf(String.valueOf(value));
                    }
                    break;
                case "Date":
                    if (value instanceof String) {
                        finalVal = DateUtil.strToDateDay(String.valueOf(value).replaceAll("[年月]", "-").replace("日", "").replace("/", "-").trim());
                    }
                    break;
                case "String":
                    if (value instanceof Double || value instanceof Integer) {
                        finalVal = String.valueOf(value);
                    } else if (value instanceof Date) {
                        finalVal = DateUtil.dateToStr((Date) value);
                    }
                    break;
            }

            Method method = pd.getWriteMethod();
            method.invoke(entity, finalVal);
        } catch (Exception e) {
            throw new RuntimeException(String.format("导入出错，字段：%s，值：%s，位置：第%d行第%d列", field, value, j + 1, k + 1), e);
        }
    }

    /**
     * 判断是否为空白行
     *
     * @param row
     * @param colNum 总列数
     * @return
     */
    private boolean isRowEmpty(XSSFRow row, Integer colNum) {
        int blank = 0;
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell == null)
                return true;
            if (cell.getCellTypeEnum() == CellType.BLANK)
                blank++;
        }
        if (blank == colNum) {
            return true;
        }
        return false;
    }

    /**
     * 根据Cell类型返回数据
     *
     * @param cell
     * @return obj
     */
    private Object getCellFormatValue(XSSFCell cell) {
        if (cell == null)
            return null;
        try {
            // 判断当前Cell的Type
            switch (cell.getCellTypeEnum()) {
                case FORMULA:
                case NUMERIC:
                    try {
                        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            return date;
                        } else {
                            return cell.getNumericCellValue();
                        }
                    } catch (Exception e) {
                        return String.valueOf(cell.getRichStringCellValue());
                    }
                case BOOLEAN:
                    return cell.getBooleanCellValue();
                case BLANK:
                    return null;
                case STRING:
                default:
                    return String.valueOf(cell.getRichStringCellValue());
            }
        } catch (Exception e) {
            throw new SysException(e.getMessage(), e);
        }
    }

}
