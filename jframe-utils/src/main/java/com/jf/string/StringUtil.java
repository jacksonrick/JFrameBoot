package com.jf.string;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Random;

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
     * 对密码进行加密
     *
     * @param origin 明文密码
     * @return
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        resultString = new String(origin);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (NoSuchAlgorithmException e) {

        }
        return resultString;
    }

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
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
        bytes = getDigest().digest(bytes);
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

    private static MessageDigest getDigest() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
