package com.jf.encrypt;

import com.jf.exception.SysException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description: 密码工具类
 * User: xujunfei
 * Date: 2018-04-26
 * Time: 16:34
 */
public class PasswordUtil {

    public static final String RANDOM_NUMBER = "1234567890";
    public static final String RANDOM_LETTER_LOW = "abcdefghijklmnopqrstuvwxyz";
    public static final String RANDOM_LETTER_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String RANDOM_SYMBOL = "!@#$%?.,*";
    public static final String[] RANDOM_ALL = {RANDOM_NUMBER, RANDOM_LETTER_LOW, RANDOM_LETTER_UPPER, RANDOM_SYMBOL};

    /**
     * 生成随机密码，数字+小写字母，8位
     *
     * @return
     */
    public static String randomPasswd() {
        return randomPasswd(8, 2);
    }

    /**
     * 生成随机密码
     *
     * @param length 密码长度，默认8
     * @param level  密码策略，1-数字 2-小写字母 3-大写字母 4-半角符号（逐级增加复杂度）；默认2，即数字+小写字母
     * @return
     */
    public static String randomPasswd(int length, int level) {
        if (level < 1 || level > 4) {
            throw new SysException("密码策略必须在1～4之间");
        }
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            String tmp = RANDOM_ALL[random.nextInt(level)];
            int number = random.nextInt(tmp.length());
            sb.append(tmp.charAt(number));
        }
        return sb.toString();
    }

    /**
     * MD5算法
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
            resultString = origin;
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
     * SHA256算法
     *
     * @param origin
     * @return
     */
    public static String SHA256Encode(String origin) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(origin.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return encodestr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
