package com.jf.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author rick
 */
public class DateUtil {

    public final static DateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public final static DateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");
    public final static DateFormat YYYY = new SimpleDateFormat("yyyy");

    /**
     * 获取当前时间
     *
     * @param type 1-YYYY_MM_DD_HH_MM_SS
     *             2-YYYY_MM_DD [Default]
     *             3-YYYY_MM
     *             4-YYYY
     * @return
     */
    public static String getCurrentTime(Integer type) {
        Date date = new Date();
        if (type == 1)
            return YYYY_MM_DD_HH_MM_SS.format(date);
        else if (type == 2)
            return YYYY_MM_DD.format(date);
        else if (type == 3)
            return YYYY_MM.format(date);
        else if (type == 4)
            return YYYY.format(date);
        return YYYY_MM_DD.format(date);
    }

    /**
     * date转string
     *
     * @param date
     * @return YYYY_MM_DD_HH_MM_SS
     */
    public static String dateToStr(Date date) {
        return YYYY_MM_DD_HH_MM_SS.format(date);
    }

    /**
     * date转string
     *
     * @param date
     * @return YYYY_MM_DD
     */
    public static String dateToStr2(Date date) {
        return YYYY_MM_DD.format(date);
    }

    /**
     * string转date
     *
     * @param dateString
     * @return
     */
    public static Date strToDate(String dateString) {
        Date date = null;
        try {
            date = YYYY_MM_DD_HH_MM_SS.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取年月
     *
     * @param flag 如果为true则有分隔符 -
     * @return 201601
     */
    public static String getYearAndMonth(boolean flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        if (flag) {
            sdf = new SimpleDateFormat("yyyy-MM");
        }
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取年
     *
     * @return 2016
     */
    public static String getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取两个时间相差秒数
     *
     * @param startDate
     * @param endDate
     * @param msec      是否显示毫秒
     * @return
     */
    public static long getSeconds(Date startDate, Date endDate, boolean msec) {
        long times = endDate.getTime() - startDate.getTime();
        if (times < -1) {
            return 0;
        }
        if (msec) {
            return times;
        } else {
            return times / 1000;
        }
    }

    /**
     * 检查某时间是否在时间段内
     *
     * @param d1 起始时间
     * @param d2 终止时间
     * @param d  获取的时间
     * @return
     */
    public static boolean between(Date d1, Date d2, Date d) {
        return d.after(d1) && d.before(d2);
    }

    /**
     * 检查某时间是否在时间段内 格式：yyyy-MM-dd
     *
     * @param d1   起始时间
     * @param d2   终止时间
     * @param date 获取的时间
     * @return
     */
    public static boolean between(String d1, String d2, String date) {
        try {
            Date date1 = YYYY_MM_DD.parse(d1);
            Date date2 = YYYY_MM_DD.parse(d2);
            Date d = YYYY_MM_DD.parse(date);
            if (d.after(date1) && d.before(date2)) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static String getMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        Calendar f = (Calendar) cal.clone();
        f.clear();
        f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        String firstday = new SimpleDateFormat("yyyy-MM-dd").format(f.getTime());
        firstday = firstday + " 00:00:00";
        return firstday;

    }

    /**
     * 获取当月的最后一天
     *
     * @return
     */
    public static String getMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        Calendar l = (Calendar) cal.clone();
        l.clear();
        l.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        l.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        l.set(Calendar.MILLISECOND, -1);
        String lastday = new SimpleDateFormat("yyyy-MM-dd").format(l.getTime());
        lastday = lastday + " 23:59:59";
        return lastday;
    }

    /**
     * 计算两个时间之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(Date startDate, Date endDate) {
        // 一天的毫秒数1000 * 60 * 60 * 24=86400000
        long days = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        days = (end - start) / 86400000;
        return days;
    }

    /**
     * 日期加上月数的时间
     *
     * @param date
     * @param month
     * @return
     */
    public static Date dateAddMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    /**
     * 日期加上天数的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期加上年数的时间
     *
     * @param date
     * @param year
     * @return
     */
    public static Date dateAddYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    /**
     * 获取传入日期是周几
     *
     * @param date
     */
    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 检查时间是否过期
     *
     * @param time 结束时间
     * @param day  天数
     * @return true 过期
     */
    public static boolean checkTime(Date time, int day) {
        Date now = new Date();
        long t = time.getTime();
        t += day * 86400000;
        Date d = new Date(t);
        return !d.after(now);
    }

    /**
     * 计算剩余时间 (多少天多少时多少分多少秒)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String remainDateToString(Date startDate, Date endDate) {
        StringBuilder result = new StringBuilder();
        if (endDate == null) {
            return "Expire";
        }
        long times = endDate.getTime() - startDate.getTime();
        if (times < -1) {
            result.append("Expire");
        } else {
            long temp = 1000 * 60 * 60 * 24;
            // 天数
            long d = times / temp;
            // 小时数
            times %= temp;
            temp /= 24;
            long h = times / temp;
            // 分钟数
            times %= temp;
            temp /= 60;
            long m = times / temp;
            // 秒数
            times %= temp;
            temp /= 60;
            long s = times / temp;

            result.append(d);
            result.append("天");
            result.append(h);
            result.append("小时");
            result.append(m);
            result.append("分");
            result.append(s);
            result.append("秒");
        }
        return result.toString();
    }

    /**
     * 日期加减
     *
     * @param date
     * @param type  Calendar.DAY_OF_MONTH
     * @param value
     * @return
     */
    private static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    /**
     * 获取距离当前时间前几秒、几分钟、几小时、几天
     *
     * @param time
     * @return
     */
    public static String getMoment(String time) {
        try {
            Date now = new Date();
            Date date = YYYY_MM_DD_HH_MM_SS.parse(time);
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            if (day > 0)
                sb.append(day + "天");
            else if (hour > 0)
                sb.append(hour + "小时");
            else if (min > 0)
                sb.append(min + "分钟");
            else if (s > 0)
                sb.append(s + "秒");
            else
                sb.append("0秒");
            sb.append("前");
            return sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
