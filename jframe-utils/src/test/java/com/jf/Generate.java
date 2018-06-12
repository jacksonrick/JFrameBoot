package com.jf;

import com.jf.generate.GenerateBeansAndMybatisUtil;
import com.jf.generate.GenerateDict;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-12
 * Time: 10:00
 */
public class Generate {

    public static void main(String[] args) {
        // generateDict();
        generateMybatis();
    }

    /**
     * 生成数据字典
     */
    public static void generateDict() {
        String target = Generate.class.getResource("").getPath() + "generate"; // 输出路径
        System.out.println("开始执行......");
        long start = System.currentTimeMillis();
        try {
            new GenerateDict().generate(target);
            System.out.println("执行完成，总用时 : " + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行出现问题，请检查");
        }
    }

    /**
     * 生成实体类和Mybatis文件
     */
    public static void generateMybatis() {
        String target = Generate.class.getResource("").getPath() + "generate/"; // 输出路径
        System.out.println("开始执行......");
        long start = System.currentTimeMillis();
        try {
            new GenerateBeansAndMybatisUtil().generate(target);
            System.out.println("执行完成，总用时 : " + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            System.out.println("执行出现问题，请检查");
            e.printStackTrace();
        }
    }

}
