package com.jf.thread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-10
 * Time: 10:44
 */
public class Test1 {

    public static void main(String[] args) {
        testConcurrentHashMap();
    }

    /**
     * 测试testConcurrentHashMap
     */
    public static void testConcurrentHashMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap();
        map.put("name", "hah");
        map.put("age", "12");
        System.out.println(map);
    }

}
