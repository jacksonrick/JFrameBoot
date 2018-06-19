package com.jf;

import com.jf.generate.GenInfo;
import com.jf.generate.GenerateBeansAndMybatisUtil;
import com.jf.generate.GenerateDict;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-12
 * Time: 10:00
 */
public class Generate {

    public static void main(String[] args) throws IOException {
//        generateMybatis();
        generateDict();
    }

    /**
     * 生成实体类和Mybatis文件
     */
    public static void generateMybatis() throws IOException {
        File directory = new File("");
        String target = directory.getCanonicalPath() + "/jframe-utils/target/generate";
        File f = new File(target);
        if (!f.exists()) {
            f.mkdir();
        }
        GenInfo info = new GenInfo()
                .url("jdbc:mysql://127.0.0.1:3306/jframe?characterEncoding=utf8&useSSL=false")
                //.url("jdbc:postgresql://192.168.24.200:5432/pay?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull")
                .driver("com.mysql.jdbc.Driver")
                //.driver("org.postgresql.Driver")
                //.schema("public")
                .username("root")
                .password("12345678")
                .path(target)
                .author("jfxu");

        System.out.println("开始执行......");
        long start = System.currentTimeMillis();
        try {
            new GenerateBeansAndMybatisUtil().generate(info);
            System.out.println("执行完成，总用时 : " + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            System.out.println("执行出现问题，请检查");
            e.printStackTrace();
        }
    }

    /**
     * 生成数据字典
     * 仅支持MySQL
     */
    public static void generateDict() throws IOException {
        File directory = new File("");
        String target = directory.getCanonicalPath() + "/jframe-utils/target/generate";
        File f = new File(target);
        if (!f.exists()) {
            f.mkdir();
        }
        GenInfo info = new GenInfo()
                .name("Jframe")
                .url("jdbc:mysql://127.0.0.1:3306/jframe?characterEncoding=utf8&useSSL=false")
                .driver("com.mysql.jdbc.Driver")
                .username("root")
                .password("12345678")
                .path(target)
                .author("jfxu");

        System.out.println("开始执行......");
        long start = System.currentTimeMillis();
        try {
            new GenerateDict().generate(info);
            System.out.println("执行完成，总用时 : " + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行出现问题，请检查");
        }
    }

}
