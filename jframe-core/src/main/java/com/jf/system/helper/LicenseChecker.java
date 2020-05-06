package com.jf.system.helper;

import com.jf.commons.LogManager;
import com.jf.exception.SysException;
import com.jf.http.HttpUtils;
import com.jf.json.JacksonUtil;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description: 简单的许可证实现
 * User: xujunfei
 * Date: 2020-02-19
 * Time: 13:10
 */
public class LicenseChecker {

    public static String macaddr = "";
    public static final String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDa9QaqTMgOsrubD4OzdHHrHdV9+u4fZuW0zOskDCIqKNn5ebnjrcZn9Zg3GUCjbBOgIrzDmTMFTn5tMs+GY6hRQmmQcClcvGH3qSafYfUWME3me8HdvRQRVlQLH0hW7s6cfyljvtdhBpuuIBGsq00+tLEYIayO4z3g7IYTm5mgJwIDAQAB";

    public static void check(String licenseFile) {
        String secret = readSecret(licenseFile);

        try {
            String str = decrypt(secret, pubkey);
            //System.out.println("还原后的字符串为: " + str);

            String[] strs = str.split("&");
            long p1 = Long.valueOf(strs[0]);
            String p2 = strs[1];

            if (!macaddr.equals(p2)) {
                throw new SysException("该License未在此Mac地址上授权");
            }

            String json = HttpUtils.doGet("http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp", null, null);
            Map<String, Object> jsonMap = JacksonUtil.jsonToMap(json);
            Map jsonMapData = (Map) jsonMap.get("data");
            long realTime = Long.valueOf(String.valueOf(jsonMapData.get("t")));
            if (p1 < realTime) {
                throw new SysException("License已过期");
            }
        } catch (Exception e) {
            LogManager.error(e.getMessage(), e);
            throw new SysException(e.getMessage());
        }

        //LogManager.info("License正常使用^_^");
    }

    private static String decrypt(String str, String publicKey) throws Exception {
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return new String(cipher.doFinal(inputByte));
    }

    private static String readSecret(String licenseFile) {
        File keyFile = new File(licenseFile);
        try (InputStream in = new FileInputStream(keyFile); BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            String str = reader.readLine();
            if (str == null) {
                throw new SysException("License读取失败");
            }
            return str;
        } catch (Exception e) {
            throw new SysException(e.getMessage());
        }
    }

    public static void getLocalMac() {
        try {
            java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            StringBuilder sb = new StringBuilder();
            ArrayList<String> tmpMacList = new ArrayList<>();
            while (en.hasMoreElements()) {
                NetworkInterface iface = en.nextElement();
                List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
                for (InterfaceAddress addr : addrs) {
                    InetAddress ip = addr.getAddress();
                    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                    if (network == null) {
                        continue;
                    }
                    byte[] mac = network.getHardwareAddress();
                    if (mac == null) {
                        continue;
                    }
                    sb.delete(0, sb.length());
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    tmpMacList.add(sb.toString());
                }
            }
            if (tmpMacList.isEmpty()) {
                throw new SysException("Mac地址获取失败");
            }
            List<String> unique = tmpMacList.stream().distinct().collect(Collectors.toList());
            if (unique.size() > 1) {
                LogManager.warn("检测到有多个Mac地址: " + unique + "，默认使用第一个");
            }
            macaddr = unique.get(0);
        } catch (Exception e) {
            throw new SysException("Mac地址获取失败");
        }
    }
}

/* 生成许可证
public class KeyGen {

    // 私钥，保密
    private static final String prikey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANr1BqpMyA6yu5sPg7N0cesd1X367h9m5bTM6yQMIioo2fl5ueOtxmf1mDcZQKNsE6AivMOZMwVOfm0yz4ZjqFFCaZBwKVy8YfepJp9h9RYwTeZ7wd29FBFWVAsfSFbuzpx/KWO+12EGm64gEayrTT60sRghrI7jPeDshhObmaAnAgMBAAECgYEAtWy3xB5w2fTxDC+TwftndRi4UHW+HxjklqYA5FKFcId+7X8WLvhLYNKX+HedHowrytFdtXjYVLpQeWzTlZg32uw9MJ4dm4FqkSiLyNYwG5b88cbVGwYw/vGy+Dqb5gRzznEiK3Narfu50SIpluvZJUUTYRcW2tw8YtmptJxRS7ECQQD3mkSk/FNn7d2bRdqBZx5ewnnuMOhAB6IxocArGV7IQq5FOVPYh3G37Q9LHSTgISG7gbZKC1JzX6XlWYxxLbzZAkEA4mINc1HDNZ8qAb+XwcBtFgRjmgM0p2DJSWST/VVTmMBGGKIUj0Ml5tApy4cmLXIwxbiJ9qGZbQdnnshnltwk/wJBAL8zMa716rJmzKoK1yD2ME2cf+ufeLl5K5aIAY1Gmhqq40gxbL3YWcheF2E5sDAjZZkWIV4sQA833TOuYvVDDIECQEZrPcfbbR+m7QgyMuCgHM62es0TpjNSlgpwcOBixGhFlwhekoql1opfwkB/P87vMGCwsKm5RuJ1ZTQtHpv8yr0CQQDWSMODLq6j4mxZHpv8bTzBkpU8UUgBw9TpOJ4Ej4LibXsNPERlQJdZbZl/z93GA9QcfVru6OD3XsPAmDmmgrzp";

    public static void main(String[] args) throws Exception {
        //String[] keys = genKeyPair();
        //System.out.println("获取到公钥：" + keys[0] + "\n私钥：" + keys[1]);

        // 这里是过期时间，格式yyyy-MM-dd HH:mm:ss
        String date = "2020-08-01 00:00:00";
        // mac地址
        String mac = "BA-FF-AA-13-BA-5F";
        long time = DateUtil.strToDate(date).getTime();

        String str = encrypt(time + "&" + mac, prikey);
        System.out.println("加密后的字符串(License)为:\n" + str);
    }

    public static String[] genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        return new String[]{publicKeyString, privateKeyString};
    }

    public static String encrypt(String data, String privateKey) throws Exception {
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey rkey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rkey);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes("UTF-8")));
    }

}
* */