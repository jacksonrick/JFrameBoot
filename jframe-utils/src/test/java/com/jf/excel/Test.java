package com.jf.excel;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-08-30
 * Time: 11:22
 */
public class Test {

    public static void main(String[] args) {
        File file = new File("/Users/xujunfei/Downloads/合肥项目8.27-2.xlsx");
        ExcelReader read = new ExcelReader();
        try {
            String jsonConfig = getConfig();
            read.read(new FileInputStream(file), jsonConfig, BankList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getConfig() {
        String jsonConfig = "[{\"name\":\"过件\",\"type\":\"A1\",\"content\":{\"fields\":[{\"name\":\"推荐人代号\",\"field\":\"phone\"},{\"name\":\"客户姓名\",\"field\":\"custname\"},{\"name\":\"终审卡种\",\"field\":\"bank\"}],\"jsons\":[\"公司名称\",\"信用额度(单位:元)\",\"公司地址区段1\",\"公司地址区段2\",\"公司地址区段3\"]}},{\"name\":\"已核访\",\"type\":\"A2\",\"content\":{\"fields\":[{\"name\":\"推荐人代号\",\"field\":\"phone\"},{\"name\":\"客户姓名\",\"field\":\"custname\"},{\"name\":\"终审卡种\",\"field\":\"bank\"}],\"jsons\":[\"公司名称\",\"推广机构\",\"核访日期\"]}}]";
        return jsonConfig;
    }

}
