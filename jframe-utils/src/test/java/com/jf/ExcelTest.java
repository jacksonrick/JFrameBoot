package com.jf;

import com.jf.excel.CustList;
import com.jf.excel.PersonModel;
import com.jf.excel.UserTest;
import com.jf.poi.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Excel 测试类
 * User: xujunfei
 * Date: 2019-03-12
 * Time: 16:36
 */
public class ExcelTest {

    public static void main(String[] args) throws Exception {
        testReaderConfig();
    }

    public static void testOPC() throws Exception {
        long start = System.currentTimeMillis();

        File file = new File("/Users/xujunfei/Downloads/源数据模板/人员组织结构.xlsx");
        InputStream is = new FileInputStream(file);
        ExcelReaderOPC xlsx2csv = new ExcelReaderOPC();
        List<String[]> datas = xlsx2csv.process(is);

        long end = System.currentTimeMillis();
        System.out.println("读取时间: " + (end - start) / 1000 + "s");
        System.out.println("总条数：" + datas.size());

        // 模拟插入数据库
        int batch = 50; // 一次插入数量
        int total = datas.size();
        int times = total % batch > 0 ? total / batch + 1 : total / batch; // 次数
        for (int i = 0; i < times; i++) {
            List<String[]> batchs = datas.subList(0, datas.size() > batch ? batch : datas.size());
            // 数据处理...
            // xxxService.insertBatch(batchs);
            for (int j = 0; j < batchs.size(); j++) {
                System.out.println(Arrays.toString(batchs.get(j)));
            }
            batchs.clear();
        }
    }

    public static void testReaderAuto() throws Exception {
        File file = new File("/Users/xujunfei/Downloads/人员明细最终版6.25.xlsx");
        InputStream is = new FileInputStream(file);
        ExcelReaderXSSAuto read = new ExcelReaderXSSAuto();
        List<PersonModel> list = read.read(is, PersonModel.class);
        for (PersonModel model : list) {
            System.out.println(model);
        }
    }

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
        File file = new File("/Users/xujunfei/Downloads/excel_test.xlsx");
        ExcelReaderConfig read = new ExcelReaderConfig();
        try {
            String jsonConfig = getConfig();
            read.read(new FileInputStream(file), jsonConfig, CustList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getConfig() {
        String jsonConfig = "[{\n" +
                "\t\"name\": \"手机号\",\n" +
                "\t\"field\": \"phone\"\n" +
                "}, {\n" +
                "\t\"name\": \"姓名\",\n" +
                "\t\"field\": \"custname\"\n" +
                "}, {\n" +
                "\t\"name\": \"邮箱\",\n" +
                "\t\"field\": \"email\"\n" +
                "}, {\n" +
                "\t\"name\": \"年龄\",\n" +
                "\t\"field\": \"age\"\n" +
                "}]";
        return jsonConfig;
    }

}
