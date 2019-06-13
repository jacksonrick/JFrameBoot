package com.jf.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * <p>Joda Time</p>
 *
 * @author rick
 */
public class DateUtil {

    private final static DateTimeFormatter FMT_YYYY_MM_DD_HH_MM_SS = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter FMT_YYYY_MM_DD = DateTimeFormat.forPattern("yyyy-MM-dd");

    private final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private final static String YYYY_MM_DD = "yyyy-MM-dd";
    private final static String YYYY_MM = "yyyy-MM";
    private final static String YYYY = "yyyy";

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
        DateTime dt = new DateTime(System.currentTimeMillis());
        if (type == 1)
            return dt.toString(YYYY_MM_DD_HH_MM_SS);
        else if (type == 2)
            return dt.toString(YYYY_MM_DD);
        else if (type == 3)
            return dt.toString(YYYY_MM);
        else if (type == 4)
            return dt.toString(YYYY);
        return dt.toString(YYYY_MM_DD);
    }

    /**
     * 获取当前时间
     *
     * @param format 自定义格式
     * @return
     */
    public static String getCurrentTime(String format) {
        DateTime dt = new DateTime(System.currentTimeMillis());
        return dt.toString(format);
    }

    /**
     * 获取当前小时
     *
     * @return
     */
    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
    }

    /**
     * 获取当前分钟
     *
     * @return
     */
    public static String getCurrentMinute() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.MINUTE));
    }

    /**
     * date转string
     *
     * @param date
     * @return YYYY_MM_DD_HH_MM_SS
     */
    public static String dateToStr(Date date) {
        DateTime dt = new DateTime(date);
        return dt.toString(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * date转string
     *
     * @param date
     * @return YYYY_MM_DD
     */
    public static String dateToStrDay(Date date) {
        DateTime dt = new DateTime(date);
        return dt.toString(YYYY_MM_DD);
    }

    /**
     * date转string
     *
     * @param date
     * @param format 自定义格式
     * @return
     */
    public static String dateToStr(Date date, String format) {
        DateTime dt = new DateTime(date);
        return dt.toString(format);
    }

    /**
     * string转date
     * YYYY_MM_DD_HH_MM_SS
     *
     * @param dateString
     * @return
     */
    public static Date strToDate(String dateString) {
        return FMT_YYYY_MM_DD_HH_MM_SS.parseDateTime(dateString).toDate();
    }

    /**
     * string转date
     * YYYY_MM_DD
     *
     * @param dateString
     * @return
     */
    public static Date strToDateDay(String dateString) {
        return FMT_YYYY_MM_DD.parseDateTime(dateString).toDate();
    }

    /**
     * string转date
     *
     * @param dateString
     * @param format     自定义格式
     * @return
     */
    public static Date strToDate(String dateString, String format) {
        DateTimeFormatter FMT = DateTimeFormat.forPattern(format);
        return FMT.parseDateTime(dateString).toDate();
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
        Date date1 = FMT_YYYY_MM_DD.parseDateTime(d1).toDate();
        Date date2 = FMT_YYYY_MM_DD.parseDateTime(d2).toDate();
        Date d = FMT_YYYY_MM_DD.parseDateTime(date).toDate();
        if (d.after(date1) && d.before(date2)) {
            return true;
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
        Date now = new Date();
        Date date = FMT_YYYY_MM_DD_HH_MM_SS.parseDateTime(time).toDate();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuffer sb = new StringBuffer("");
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
    }
}
