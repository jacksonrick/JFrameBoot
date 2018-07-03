package com.jf.jvm;

import com.jf.string.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * Description: URLClassLoader
 * User: xujunfei
 * Date: 2018-04-09
 * Time: 14:41
 */
public class MyClassLoad {

    public static void main(String[] args) {
        try {
            URL url = new URL("file:/Users/xujunfei/Desktop/Project/Idea/JFrameBoot/jframe-utils/target/jframe-utils.jar");
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            Class cls = Class.forName("com.jf.string.StringUtil", true, classLoader);
            StringUtil util = (StringUtil) cls.newInstance();

            System.out.println(util.getOrderCode());
            System.out.println(util.getSmsCode(6));
            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
