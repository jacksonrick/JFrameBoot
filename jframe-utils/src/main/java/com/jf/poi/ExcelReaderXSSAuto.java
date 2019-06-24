package com.jf.poi;

import com.jf.annotation.excel.Fields;
import com.jf.date.DateUtil;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 注解式导入Excel
 * <p>支持excel2007以上版本</p>
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
                        String fieldName = fs[i].getName(); // 字段名
                        String fieldType = fs[i].getType().getSimpleName(); // 字段类型
                        String colName = field.value(); // 指定对应列名
                        if (colName.equals(name)) {
                            Object val = getCellFormatValue(row.getCell(k));
                            // 枚举可能出现的情况
                            if ("Integer".equals(fieldType)) {
                                if (val instanceof Double) {
                                    setEntityValue(fieldName, new Double(String.valueOf(val)).intValue(), entity);
                                } else if (val instanceof String) {
                                    setEntityValue(fieldName, Integer.valueOf(String.valueOf(val)), entity);
                                } else {
                                    setEntityValue(fieldName, val, entity);
                                }
                            } else if ("Date".equals(fieldType)) {
                                if (val instanceof String) {
                                    setEntityValue(fieldName, DateUtil.strToDateDay(String.valueOf(val).replaceAll("[年月]", "-").replace("日", "").replace("/", "-").trim()), entity);
                                } else {
                                    setEntityValue(fieldName, val, entity);
                                }
                            } else if ("String".equals(fieldType)) {
                                if (val instanceof Double || val instanceof Integer) {
                                    setEntityValue(fieldName, String.valueOf(val), entity);
                                } else {
                                    setEntityValue(fieldName, val, entity);
                                }
                            } else {
                                setEntityValue(fieldName, val, entity);
                            }
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
    private <T> void setEntityValue(String field, Object value, T entity) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field, entity.getClass());
            Method method = pd.getWriteMethod();
            method.invoke(entity, value);
        } catch (Exception e) {
            throw new RuntimeException("导入出错，字段：" + field + "，值：" + value, e);
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
     * 根据XSSFCell类型设置数据
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
                case FORMULA: {
                    // 判断当前的cell是否为Date
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        Date date = cell.getDateCellValue();
                        return date;
                    } else { // 如果是纯数字
                        // 取得当前Cell的数值
                        return cell.getNumericCellValue();
                    }
                    // *NUMERIC也会有类似处理
                }
                case NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        return date;
                    } else {
                        return cell.getNumericCellValue();
                    }
                case STRING:
                    return cell.getRichStringCellValue().getString();
                case BOOLEAN:
                    return cell.getBooleanCellValue();
                case BLANK:
                    return null;
                default:
                    return cell.getRichStringCellValue().getString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
