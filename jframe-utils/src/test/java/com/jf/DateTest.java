package com.jf;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class DateTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        LocalDate start = new LocalDate(2017, 12, 30);
        LocalDate end = new LocalDate(2018, 1, 2);
        int days = Days.daysBetween(start, end).getDays();
        System.out.println(days);
    }

}
