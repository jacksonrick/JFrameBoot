package com.jf;

import com.jf.obj.BeanUtil;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Map;

/**
 * Created by xujunfei on 2016/12/8.
 */
public class MainTest {

    public static void main(String[] args) {
        test02();
    }

    public static void test01() {
        TestUser user = new TestUser();
        user.setAge(1);
        user.setFlag(true);
        Map<String, Object> map = BeanUtil.beanToMap(user);
        System.out.println(map);
    }

    public static void test02() {
        System.out.println(MainTest.class.getClassLoader().getResource("logo.png").toString());
    }

    public static void test03() {
        LocalDate start = new LocalDate(2017, 12, 30);
        LocalDate end = new LocalDate(2018, 1, 2);
        int days = Days.daysBetween(start, end).getDays();
        System.out.println(days);
    }

}
