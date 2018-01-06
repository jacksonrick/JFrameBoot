package com.jf.convert;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 转换工具类
 * <p>金额、时间、字符串</p>
 *
 * @author rick
 * @version 2.0
 */
public class Convert {

    public static void main(String[] args) {
    }

    /**
     * 浮点数相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleAdd(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return doubleFormat(b1.add(b2).doubleValue());
    }

    /**
     * 浮点数相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleSub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return doubleFormat(b1.subtract(b2).doubleValue());
    }

    /**
     * 浮点数相乘
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleMul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return doubleFormat(b1.multiply(b2).doubleValue());
    }

    /**
     * 不定参数相加【请确保最少有两个参数】
     *
     * @param doubles
     * @return
     */
    public static double doublesAdd(double... doubles) {
        BigDecimal b1 = new BigDecimal(Double.toString(doubles[0]));
        for (int i = 1; i < doubles.length; i++) {
            BigDecimal b2 = new BigDecimal(Double.toString(doubles[i]));
            b1 = b1.add(b2);
        }
        return doubleFormat(b1.doubleValue());
    }

    /**
     * 不定参数相减【请确保最少有两个参数】
     *
     * @param doubles
     * @return
     */
    public static double doublesSub(double... doubles) {
        BigDecimal b1 = new BigDecimal(Double.toString(doubles[0]));
        for (int i = 1; i < doubles.length; i++) {
            BigDecimal b2 = new BigDecimal(Double.toString(doubles[i]));
            b1 = b1.subtract(b2);
        }
        return doubleFormat(b1.doubleValue());
    }

    /**
     * 不定参数相乘【请确保最少有两个参数】
     *
     * @param doubles
     * @return
     */
    public static double doublesMul(double... doubles) {
        BigDecimal b1 = new BigDecimal(Double.toString(doubles[0]));
        for (int i = 1; i < doubles.length; i++) {
            BigDecimal b2 = new BigDecimal(Double.toString(doubles[i]));
            b1 = b1.multiply(b2);
        }
        return doubleFormat(b1.doubleValue());
    }


    /**
     * double保留两位小数点
     *
     * @param d
     * @return
     */
    public static double doubleFormat(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(d));
    }

    /**
     * intToInt
     *
     * @param val
     * @param defaultValue 默认值
     * @return
     */
    public static Integer intToInt(Integer val, Integer defaultValue) {
        int d = defaultValue;
        if (val == null) {
            return d;
        }
        return val;
    }

    /**
     * stringToInt
     *
     * @param val
     * @param defaultValue 默认值
     * @return
     */
    public static Integer stringToInt(String val, Integer defaultValue) {
        Integer d = defaultValue;
        try {
            d = Integer.parseInt(val);
        } catch (Exception e) {

        }
        return d;
    }

    /**
     * 将数字1或0转换为boolean
     *
     * @param state
     * @return
     */
    public static Boolean stringToBoolean(String state) {
        int t = stringToInt(state, 0);
        return t == 1;
    }

    /**
     * stringToDouble
     *
     * @param val
     * @param defaultValue
     * @return
     */
    public static Double stringToDouble(String val, double defaultValue) {
        double d = defaultValue;
        try {
            d = Double.parseDouble(val);
        } catch (Exception e) {

        }
        return d;
    }

    /**
     * stringToString
     *
     * @param val
     * @param defaultValue 默认值
     * @return
     */
    public static String stringToString(String val, String defaultValue) {
        if (val != null && !"".equals(val.trim())) {
            return val;
        }
        return defaultValue;
    }

}
