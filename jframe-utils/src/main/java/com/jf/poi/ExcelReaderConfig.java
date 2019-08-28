package com.jf.poi;

import com.jf.date.DateUtil;
import com.jf.json.JacksonUtil;
import com.jf.poi.model.ExcelJsonConfig;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 配置式读取
 * User: xujunfei
 * Date: 2018-08-30
 * Time: 11:10
 */
public class ExcelReaderConfig {

    private POIFSFileSystem fs;
    private XSSFWorkbook wb;
    private XSSFSheet sheet;

    /**
     * 读取json配置
     *
     * @param is
     * @param jsonConfig
     * @param clz
     * @param <T>
     * @throws Exception
     */
    public <T> void read(InputStream is, String jsonConfig, Class<T> clz) throws Exception {
        // 创建工作簿
        wb = new XSSFWorkbook(is);

        List<ExcelJsonConfig> config = JacksonUtil.jsonToList(jsonConfig, ExcelJsonConfig.class);
        System.out.println("JSON配置: " + config);
        // 存储读取的记录
        List<T> list = new ArrayList<>();

        // 默认读取第一个Sheet
        sheet = wb.getSheetAt(0);
        // 读取总行数
        int rowNum = sheet.getLastRowNum();
        // 第一列-标题
        XSSFRow row0 = sheet.getRow(0);
        XSSFRow row = null;
        // 读取总列数-第一行
        int colNum = row0.getPhysicalNumberOfCells();
        for (int j = 1; j <= rowNum; j++) { // 读行-数据从第二行起
            T entity = clz.newInstance(); // 实例化实体
            row = sheet.getRow(j); // 获取行记录
            int k = 0;
            Map<String, String> jsonMap = new HashMap<>();
            while (k < colNum) { // 读列
                String cell0 = getStringValue(row0.getCell(k));
                // 比对字段
                for (ExcelJsonConfig field : config) {
                    if (field.getName().equals(cell0)) {
                        Object value = getCellFormatValue(row.getCell(k));
                        setEntityValue(field.getField(), value, entity, j, k);
                    }
                }
                k++;
            }
            list.add(entity);
        }

        for (T t : list) {
            System.out.println(t.toString());
        }
    }

    /**
     * @param field
     * @param value
     * @param entity
     * @param <T>
     * @throws Exception
     */
    private <T> void setEntityValue(String field, Object value, T entity, int j, int k) throws Exception {
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

    private <T> T getObject(Class<T> c) throws InstantiationException, IllegalAccessException {
        // 创建泛型对象
        T t = c.newInstance();
        return t;
    }

    /**
     * @param cell
     * @return
     */
    private String getStringValue(XSSFCell cell) {
        String cellvalue = "";
        if (cell == null) {
            return cellvalue;
        }
        try {
            cellvalue = cell.getRichStringCellValue().getString();
        } catch (Exception e) {
        }
        return cellvalue;
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
            e.printStackTrace();
            return null;
        }
    }
}
