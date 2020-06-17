package com.jf.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证器
 * <p>true-校验通过</p>
 *
 * @author rick
 * @version 2.0
 */
public class ValidUtil {

    /**
     * 校验字符串长度
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean length(String str, int min, int max) {
        return str.length() >= min && str.length() <= max;
    }

    /**
     * 校验字符串长度【str必须】
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean string(String str, int min, int max) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        return str.length() >= min && str.length() <= max;
    }

    /**
     * 小数
     *
     * @param str
     * @return
     */
    public static boolean decimal(String str, boolean need) {
        return regexp(str, need, "^(\\d+)(\\.?)(\\d{0,2})$");
    }

    /**
     * 正整数
     *
     * @param str
     * @return
     */
    public static boolean integer(String str, boolean need) {
        return regexp(str, need, "^(0|[1-9][0-9]*)$");
    }

    /**
     * 手机号
     *
     * @param str
     * @return
     */
    public static boolean phone(String str, boolean need) {
        return regexp(str, need, "^1[3|4|5|7|8][0-9]\\d{8}$");
    }

    /**
     * 邮箱
     *
     * @param str
     * @return
     */
    public static boolean email(String str, boolean need) {
        return regexp(str, need, "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    /**
     * 身份证号码
     *
     * @param str
     * @return
     */
    public static boolean idcard(String str, boolean need) {
        return regexp(str, need, "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
    }

    /**
     * 网址
     *
     * @param str
     * @return
     */
    public static boolean url(String str, boolean need) {
        return regexp(str, need, "^(http(s)?:\\/\\/)?(www\\.)?[\\w-]+\\.\\w{2,4}(\\/)?$");
    }

    /**
     * 仅数字和字母
     *
     * @param str
     * @return
     */
    public static boolean numberLetter(String str, boolean need) {
        return regexp(str, need, "^[A-Za-z0-9]+$");
    }

    /**
     * 折扣 0-1
     *
     * @param str
     * @param need
     * @return
     */
    public static boolean discount(String str, boolean need) {
        return regexp(str, need, "\\b0(\\.\\d{1,2})\\b");
    }

    /**
     * 0-1
     *
     * @param number
     * @return
     */
    public static boolean zeroToOne(double number) {
        return number >= 0 && number <= 1;
    }

    static Pattern pattern = Pattern.compile("<[^>]+>");

    /**
     * 去除HTML标签
     *
     * @param str
     * @return
     */
    public static String delHTMLTag(String str) {
        if (StringUtil.isBlank(str)) {
            return "";
        }
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.replaceAll("");
        }
        return "";
    }

    private static boolean regexp(String str, boolean need, String reg) {
        if (need) {
            if (StringUtil.isBlank(str)) {
                return false;
            }
            return pattern(str, reg);
        }
        if (StringUtil.isNotBlank(str)) {
            return pattern(str, reg);
        }
        return true;
    }

    public static boolean pattern(String str, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

}
