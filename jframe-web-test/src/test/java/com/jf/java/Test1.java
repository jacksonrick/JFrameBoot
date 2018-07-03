package com.jf.jvm;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-09
 * Time: 11:23
 */
public class Test1 {

    public static void main(String[] args) {
        ClassLoader cl = Test1.class.getClassLoader();

        System.out.println(cl.toString());
    }

}
