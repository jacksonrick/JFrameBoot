package com.jf;

import com.jf.excel.BankList;
import com.jf.excel.UserTest;
import com.jf.poi.ExcelReaderConfig;
import com.jf.poi.ExcelWriterSXSS;
import com.jf.poi.ExcelWriterSXSSAuto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-12
 * Time: 16:36
 */
public class ExcelTest {

    /**
     * SXSS写入
     *
     * @throws Exception
     */
    public static void testSXSS() throws Exception {
        long start = System.currentTimeMillis();

        // 测试数据
        int total = 1000000;
        List<String> tests = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            tests.add("A,B,C,D,E,F,G,H,I,J,K,L,M,N");
        }

        ExcelWriterSXSS sxss = new ExcelWriterSXSS();
        sxss.write(tests);

        System.out.println("time: " + (System.currentTimeMillis() - start));
    }

    /**
     * 注解方式，大批量数据分页写入
     *
     * @throws Exception
     */
    public static void testSXSSAuto() throws Exception {
        long start = System.currentTimeMillis();
        // 测试数据
        int total = 1000000;
        int pagesize = 10000;
        List<UserTest> tests = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            UserTest user = new UserTest(i + 1);
            user.setB("b");
            user.setC("c");
            user.setD("d");
            user.setE("e");
            user.setF("f");
            user.setG("1");
            tests.add(user);
        }

        ExcelWriterSXSSAuto sxss = new ExcelWriterSXSSAuto(UserTest.class);

        // 次数
        int times = total % pagesize > 0 ? total / pagesize + 1 : total / pagesize;
        for (int i = 0; i < times; i++) {
            List<UserTest> fd = tests.subList(0, pagesize);
            sxss.write(fd);
            fd.clear();
        }
        sxss.export("/Users/xujunfei/Downloads/");

        System.out.println("time: " + (System.currentTimeMillis() - start));
    }

    /**
     * JSON配置字段 写入
     */
    public static void testReaderConfig() {
        File file = new File("/Users/xujunfei/Downloads/1.xlsx");
        ExcelReaderConfig read = new ExcelReaderConfig();
        try {
            String jsonConfig = getConfig();
            read.read(new FileInputStream(file), jsonConfig, BankList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getConfig() {
        String jsonConfig = "[{\"name\":\"过件\",\"type\":\"A1\",\"content\":{\"fields\":[{\"name\":\"推荐人代号\",\"field\":\"phone\"},{\"name\":\"客户姓名\",\"field\":\"custname\"},{\"name\":\"终审卡种\",\"field\":\"bank\"}],\"jsons\":[\"公司名称\",\"信用额度(单位:元)\",\"公司地址区段1\",\"公司地址区段2\",\"公司地址区段3\"]}},{\"name\":\"已核访\",\"type\":\"A2\",\"content\":{\"fields\":[{\"name\":\"推荐人代号\",\"field\":\"phone\"},{\"name\":\"客户姓名\",\"field\":\"custname\"},{\"name\":\"终审卡种\",\"field\":\"bank\"}],\"jsons\":[\"公司名称\",\"推广机构\",\"核访日期\"]}}]";
        return jsonConfig;
    }

}
