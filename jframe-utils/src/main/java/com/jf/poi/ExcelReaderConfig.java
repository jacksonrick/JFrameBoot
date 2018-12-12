package com.jf.poi;

import com.jf.json.JacksonUtil;
import com.jf.poi.model.EConfig;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
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
     * @param is
     * @param jsonConfig
     * @param clz
     * @param <T>
     * @throws Exception
     */
    public <T> void read(InputStream is, String jsonConfig, Class<T> clz) throws Exception {
        // 创建工作簿
        wb = new XSSFWorkbook(is);

        List<EConfig> configs = JacksonUtil.jsonToList(jsonConfig, EConfig.class);
        System.out.println("配置: " + configs);

        Field[] ff = clz.getDeclaredFields();
        for (Field f : ff) {
            System.out.print(f.getName() + "\t");
        }

        System.out.println("共有Sheet：" + wb.getNumberOfSheets());
        // 存储读取的记录
        List<T> list = new ArrayList<>();
        System.out.println("\n-----------------------------");

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            sheet = wb.getSheetAt(i);
            System.out.println("第" + (i + 1) + "个Sheet名称：" + sheet.getSheetName());

            // 遍历Sheet
            for (EConfig sht : configs) {
                if (!sht.getName().equals(sheet.getSheetName())) {
                    continue;
                }

                // 获取列配置
                List<EConfig.Fields> fields = sht.getContent().getFields();
                String[] jsons = sht.getContent().getJsons();
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
                        for (EConfig.Fields f1 : fields) {
                            String value = getCellFormatValue(row.getCell(k));
                            if (f1.getName().equals(cell0)) {
                                setEntityValue(f1.getField(), value, entity);
                            }
                        }
                        // 比对JSON
                        for (String arr : jsons) {
                            if (arr.equals(cell0)) {
                                String value = getCellFormatValue(row.getCell(k));
                                jsonMap.put(arr, value);
                                break;
                            }
                        }
                        k++;
                    }
                    setEntityValue("step", sht.getType(), entity);
                    setEntityValue("jsons", JacksonUtil.objectToJson(jsonMap), entity);
                    list.add(entity);
                }
            }
        }

        System.out.println("---------------------");
        for (T t : list) {
            System.out.println(t);
        }
    }

    /**
     * @param field
     * @param value
     * @param entity
     * @param <T>
     * @throws Exception
     */
    private <T> void setEntityValue(String field, String value, T entity) throws Exception {
        // 方法1
        /*// 属性的set方法，必须以set开头，is不支持
        String setMethodName = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
        Method setMethod = entity.getClass().getMethod(setMethodName, new Class[]{});
        // 执行set方法
        setMethod.invoke(entity, new Object[]{});*/

        // 方法2
        PropertyDescriptor pd = new PropertyDescriptor(field, entity.getClass());
        Method method = pd.getWriteMethod();
        method.invoke(entity, value);
    }

    private <T> T getObject(Class<T> c) throws InstantiationException, IllegalAccessException {
        // 创建泛型对象
        T t = c.newInstance();
        return t;
    }

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

    private String getCellFormatValue(XSSFCell cell) {
        String cellvalue = "";
        if (cell == null) {
            return cellvalue;
        }
        try {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        cellvalue = sdf.format(date);

                    } else { // 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case XSSFCell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cell.setCellType(CellType.STRING);
                    cellvalue = cell.getRichStringCellValue().getString();
            }
        } catch (Exception e) {
            cellvalue = cell.getRichStringCellValue().getString();
        }
        return cellvalue;
    }
}
