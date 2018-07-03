package com.jf.jvm;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-10
 * Time: 10:55
 */
public class Java8 {

    public static void main(String[] args) {
        testStream1();
        testStream2();
        testStream3();
        testStream4();
        testStream5();
    }

    public static void testStream1() {
        List<String> list = Lists.newArrayList("aaa", "bbb", "cccc", "dd");
        List<String> result = list.stream()
                .filter(e -> e.length() >= 3)
                //.map(e -> String.valueOf(e))
                .collect(Collectors.toList());
        System.out.println(result);
    }

    public static void testStream2() {
        List<String> list = Lists.newArrayList("aaa", "bbb", "cccc", "dd");
        List<String> result = list.stream()
                .parallel()
                .filter(e -> e.length() >= 3)
                //.map(e -> String.valueOf(e))
                .sorted()
                .collect(Collectors.toList());
        System.out.println(result);
    }

    public static void testStream3() {
        List<String> list = Lists.newArrayList("aaa", "bbb", "cccc", "dd");
        Long count = list.stream()
                .parallel()
                .filter(e -> e.length() >= 3)
                //.map(e -> String.valueOf(e))
                .sorted()
                .count();
        System.out.println(count);
    }

    /**
     * toArray
     */
    public static void testStream4() {
        Object[] objs = Stream.of("aaa", "bbbb", "cc", "ddd").toArray();
        String[] strs = Stream.of("aaa", "bbbb", "cc", "ddd").toArray(String[]::new);

        System.out.println(Arrays.asList(objs));
        System.out.println(Arrays.asList(strs));
    }

    /**
     * toMap
     */
    public static void testStream5() {
        Map<String, String> map = Stream.of("hello world").collect(Collectors.toMap(s -> s.substring(0, 5), s -> s));
        System.out.println(map);
    }

}
