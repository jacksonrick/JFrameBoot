package com.jf.string;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符处理工具类
 *
 * @author rick
 * @version v3.0
 */
public class StringUtil {

    /**
     * 获取订单编号（时间戳+8位随机数）
     *
     * @return
     */
    public static String getOrderCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return sdf.format(System.currentTimeMillis()) + "" + (int) (Math.random() * 90000 + 10000);
    }

    /**
     * 获取手机验证码
     *
     * @param size 位数
     * @return
     */
    public static String getSmsCode(int size) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 将字符串转换为一个新的字符数组
     *
     * @param answer
     * @return
     */
    public static String[] strToArray(String answer) {
        if (StringUtils.isNotBlank(answer)) {
            return answer.split("");
        }
        return null;
    }

    /**
     * 获取随机代码
     *
     * @param size 长度
     * @return
     */
    public static String getRandomCode(int size) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 文件随机命名
     * <p>规则：当前时间(Long)+5位随机数+原文件后缀</p>
     *
     * @param file 源文件
     * @return
     */
    public static String randomFilename(String file) {
        String temp = file.substring(file.lastIndexOf("."));
        return System.currentTimeMillis() + "" + (int) (Math.random() * 90000 + 10000) + temp;
    }

    /**
     * 文件随机命名
     *
     * @return
     */
    public static String randomFilename() {
        return System.currentTimeMillis() + "" + (int) (Math.random() * 90000 + 10000);
    }

    /**
     * 获取文件类型【后缀】
     *
     * @param file
     * @return
     */
    public static String getFileType(String file) {
        return file.substring(file.lastIndexOf(".") + 1);
    }

    public static final String[] number = new String[]{"十", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    /**
     * 阿拉伯数字转中文
     *
     * @return
     */
    public static String getColumnNum(String num) {
        char[] cr = num.toCharArray();
        int length = cr.length;
        int[] arr = new int[length];
        for (int i = 0; i < cr.length; i++) {
            arr[i] = Integer.parseInt(cr[i] + "");
        }
        StringBuffer strBuffer = new StringBuffer();
        switch (length) {
            case 1:
                strBuffer.append(number[arr[0]]);
                break;
            case 2:
                if ("10".equals(num)) {
                    strBuffer.append(number[arr[1]]);
                    break;
                } else if (arr[0] == 1) {
                    strBuffer.append(number[0]).append(number[arr[1]]);
                    break;
                } else if (arr[1] == 0) {
                    strBuffer.append(number[arr[0]]).append(number[arr[1]]);
                } else {
                    strBuffer.append(number[arr[0]]).append(number[0]).append(number[arr[1]]);
                }
                break;
            default:
                break;
        }
        return strBuffer.toString();
    }

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

    private static final int SESSION_ID_BYTES = 16;

    /**
     * 构造SESSIONID
     *
     * @return
     */
    public static synchronized String getTokenId() {
        // 取随机数发生器, 默认是SecureRandom
        Random random = new SecureRandom();
        byte bytes[] = new byte[SESSION_ID_BYTES];
        // 产生16字节的byte
        random.nextBytes(bytes);
        // 取摘要,默认是"MD5"算法
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        // 转化为16进制字符串
        for (int i = 0; i < bytes.length; i++) {
            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
            byte b2 = (byte) (bytes[i] & 0x0f);
            if (b1 < 10)
                result.append((char) ('0' + b1));
            else
                result.append((char) ('A' + (b1 - 10)));
            if (b2 < 10)
                result.append((char) ('0' + b2));
            else
                result.append((char) ('A' + (b2 - 10)));
        }
        return (result.toString());
    }

    /**
     * 将驼峰命名改为下划线
     * 如：bBindAdsd -> b_bind_adsd
     *
     * @param str
     * @return
     */
    public static StringBuffer underline(String str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        } else {
            return sb;
        }
        return underline(sb.toString());
    }
}
