package com.jf;

import com.jf.obj.BeanUtil;

import java.util.Map;

/**
 * Created by xujunfei on 2016/12/8.
 */
public class MainTest {

    public static void main(String[] args) {
        test02();
    }

    public static void test01(){
        User user=new User();
        user.setAge(1);
        user.setFlag(true);
        Map<String, Object> map = BeanUtil.beanToMap(user);
        System.out.println(map);
    }

    public static void test02(){
        System.out.println(MainTest.class.getResource("/").toString());
    }

}
