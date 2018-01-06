package com.jf.convert;

import java.math.BigDecimal;

/**
 * Created by xujunfei on 2016/12/26.
 */
public class Math {

    /**
     * 舍进小数方式
     */
    public static final int ROUND_HALF = BigDecimal.ROUND_HALF_UP;

    /**
     * 默认运算时精度
     */
    public static final int DEF_ARITHMETIC_ACCURACY = 10;

    /**
     * 默认结果精度
     */
    public static final int DEF_RESULT_ACCURACY = 2;

    private Math() {
    }

    /**
     * 格式化数字保留指定精度
     *
     * @param num      待格式化数字
     * @param accuracy 指定精度
     * @return
     */
    public static double fmt(double num, int accuracy) {
        BigDecimal b = new BigDecimal(Double.toString(num));
        return fmt(b, accuracy).doubleValue();
    }

    /**
     * 格式化数字保留两位小数
     *
     * @param num 待格式化数字
     * @return
     */
    public static double fmt2(double num) {
        return fmt(num, 2);
    }

    /**
     * 格式化 BigDecimal 保留两位小数
     *
     * @param num 待格式化数字
     * @return BigDecimal
     */
    public static BigDecimal fmt2(BigDecimal num) {
        return fmt(num, 2);
    }

    /**
     * 格式化 BigDecimal 保留指定精度的结果
     *
     * @param num      待格式化数字
     * @param accuracy 指定精度
     * @return BigDecimal
     */
    public static BigDecimal fmt(BigDecimal num, int accuracy) {
        return num.setScale(accuracy, ROUND_HALF);
    }

    /**
     * 按近似模式格式化BigDecimal
     *
     * @param num          待格式化的数字
     * @param accuracy     指定精度
     * @param roundingMode 近似模式 可选值有:
     *                     <ul>
     *                     <li>0 直接进位</li>
     *                     <li>1 直接舍弃</li>
     *                     <li>2 大于0的直接进位，小于0的直接舍弃</li>
     *                     <li>3 小于0的直接进位，大于0的直接舍弃</li>
     *                     <li>4 四舍五入</li>
     *                     <li>5 四舍五入，精度之后的值等于0.5，看精度位数，奇进偶不进</li>
     *                     <li>6 如果舍弃部分左边的数字为奇数，则舍入行为与 4相同；如果为偶数，则舍入行为与5相同。</li>
     *                     </ul>
     * @return BigDecimal
     */
    public static BigDecimal fmt(BigDecimal num, int accuracy, int roundingMode) {
        return num.setScale(accuracy, roundingMode);
    }

    /**
     * 按近似模式 格式化BigDecimal
     *
     * @param num          待格式化的数字
     * @param accuracy     指定精度
     * @param roundingMode 近似模式 可选值有:
     *                     <ul>
     *                     <li>0 直接进位</li>
     *                     <li>1 直接舍弃</li>
     *                     <li>2 大于0的直接进位，小于0的直接舍弃</li>
     *                     <li>3 小于0的直接进位，大于0的直接舍弃</li>
     *                     <li>4 四舍五入</li>
     *                     <li>5 四舍五入，精度之后的值等于0.5，看精度位数，奇进偶不进</li>
     *                     <li>6 如果舍弃部分左边的数字为奇数，则舍入行为与 4相同；如果为偶数，则舍入行为与5相同。</li>
     *                     </ul>
     * @return double
     */
    public static double fmtBigDecimal(BigDecimal num, int accuracy, int roundingMode) {
        return num.setScale(accuracy, roundingMode).doubleValue();
    }

    /**
     * 加法运算
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return double
     */
    public static double add(double num1, double num2) {
        BigDecimal v1 = fmt(new BigDecimal(num1), DEF_ARITHMETIC_ACCURACY);
        BigDecimal v2 = fmt(new BigDecimal(num2), DEF_ARITHMETIC_ACCURACY);
        return v1.add(v2).doubleValue();
    }

    /**
     * 减法运算
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return double (num1-num2)
     */
    public static double sub(double num1, double num2) {
        BigDecimal v1 = fmt(new BigDecimal(num1), DEF_ARITHMETIC_ACCURACY);
        BigDecimal v2 = fmt(new BigDecimal(num2), DEF_ARITHMETIC_ACCURACY);
        return v1.subtract(v2).doubleValue();
    }

    /**
     * 乘法运算
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return double
     */
    public static double mul(double num1, double num2) {
        BigDecimal v1 = fmt(new BigDecimal(num1), DEF_ARITHMETIC_ACCURACY);
        BigDecimal v2 = fmt(new BigDecimal(num2), DEF_ARITHMETIC_ACCURACY);
        return v1.multiply(v2).doubleValue();
    }

    /**
     * 除法运算
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return double
     */
    public static double div(double num1, double num2) {
        BigDecimal v1 = fmt(new BigDecimal(num1), DEF_ARITHMETIC_ACCURACY);
        BigDecimal v2 = fmt(new BigDecimal(num2), DEF_ARITHMETIC_ACCURACY);
        return v1.divide(v2, DEF_ARITHMETIC_ACCURACY, ROUND_HALF).doubleValue();
    }

    /**
     * 判断是否整除( num1/num2 是否为整数)
     *
     * @param num1 被除数
     * @param num2 除数
     * @return boolean 是否整除 (true 整除、false 不整除)
     */
    public static boolean isAliquot(double num1, double num2) {
        double v1 = fmt(num1, DEF_ARITHMETIC_ACCURACY);
        double v2 = fmt(num2, DEF_ARITHMETIC_ACCURACY);
        return (long) (div(v1, v2)) == div(v1, v2);
    }

    /**
     * 比较两个数的大小
     *
     * @param num1     比较数 1
     * @param num2     比较数 2
     * @param accuracy 指定精度
     * @return int (1:数1大、0:两数相等、-1:数2大)
     */
    public static int compare(double num1, double num2, int accuracy) {
        double v1 = fmt(num1, accuracy);
        double v2 = fmt(num2, accuracy);
        if (v1 > v2) {
            return 1;
        }
        if (v1 < v2) {
            return -1;
        }
        return 0;
    }

    /**
     * 保留两位小数比较两个数的大小
     *
     * @param num1 比较数 1
     * @param num2 比较数 2
     * @return int (1:数1大、0:两数相等、-1:数2大)
     */
    public static int compare2(double num1, double num2) {
        double v1 = fmt2(num1);
        double v2 = fmt2(num2);
        if (v1 > v2) {
            return 1;
        }
        if (v1 < v2) {
            return -1;
        }
        return 0;
    }
}

