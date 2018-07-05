package com.jf.cloud.common;

/**
 * 字符处理工具类
 *
 * @author rick
 * @from jframe-utils
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

}
