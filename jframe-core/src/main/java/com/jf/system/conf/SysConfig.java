package com.jf.system.conf;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description: 系统自定义全局配置变量
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 15:52
 */
@Configuration
@ConfigurationProperties(prefix = "system")
public class SysConfig {

    // 每页显示数量->BaseVo
    //public static Integer PAGE_SIZE = 20;

    // SESSION NAME
    public static String SESSION_USER = "user";
    public static String SESSION_ADMIN = "admin";
    public static String SESSION_RAND = "rand";
    public static String SESSION_SMS = "sms";

    // TOKEN NAME
    public static final String TOKEN = "token";
    public static final String TOKEN_HEADER = "header";
    public static final String TOKEN_COOKIE = "cookie";

    //// 以下来自 application.yml 自定义配置

    // app key
    private String appkey;
    // 静态文件IP地址
    private String staticHost;
    // 静态文件目录,以 / 结尾
    private String staticPath;
    // 日志文件目录,以 / 结尾
    private String logPath;
    // 支付宝
    private SysConfig.Aliyun aliyun;
    // 微信
    private SysConfig.Wechat wechat;
    // 极验验证
    private SysConfig.Geetest geetest;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getStaticHost() {
        return staticHost;
    }

    public void setStaticHost(String staticHost) {
        this.staticHost = staticHost;
    }

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "appkey='" + appkey + '\'' +
                ", staticHost='" + staticHost + '\'' +
                ", staticPath='" + staticPath + '\'' +
                ", logPath='" + logPath + '\'' +
                '}';
    }

    // internal java config

    public SysConfig.Aliyun getAliyun() {
        return aliyun;
    }

    public void setAliyun(SysConfig.Aliyun aliyun) {
        this.aliyun = aliyun;
    }

    public SysConfig.Wechat getWechat() {
        return wechat;
    }

    public void setWechat(SysConfig.Wechat wechat) {
        this.wechat = wechat;
    }

    public SysConfig.Geetest getGeetest() {
        return geetest;
    }

    public void setGeetest(SysConfig.Geetest geetest) {
        this.geetest = geetest;
    }

    public static class Aliyun {
        // 支付宝-合作身份者ID，以2088开头由16位纯数字组成的字符串
        private String partner;
        // 支付宝-APPID
        private String appId;
        // 支付宝-账号
        private String seller;
        // 支付宝-接收通知的接口
        private String notifyUrl;
        // 支付宝-商户私钥,需要PKCS8格式,使用支付宝提供工具生成
        private String rsaPrivateKey;
        // 支付宝-公钥，使用支付宝提供工具生成后上传至开发者平台=>点击查看支付宝公钥=>复制
        private String publicKey;

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public String getRsaPrivateKey() {
            return rsaPrivateKey;
        }

        public void setRsaPrivateKey(String rsaPrivateKey) {
            this.rsaPrivateKey = rsaPrivateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        @Override
        public String toString() {
            return "Aliyun{" +
                    "partner='" + partner + '\'' +
                    ", appId='" + appId + '\'' +
                    ", seller='" + seller + '\'' +
                    ", notifyUrl='" + notifyUrl + '\'' +
                    ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                    ", publicKey='" + publicKey + '\'' +
                    '}';
        }
    }

    public static class Wechat{
        // 微信-在开发平台登记的app应用
        private String appid;
        // 微信-商户号
        private String partner;
        // 微信-商户在微信平台设置的32位长度的api秘钥
        private String partnerKey;
        // 微信-异步通知地址
        private String notifyUrl;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getPartnerKey() {
            return partnerKey;
        }

        public void setPartnerKey(String partnerKey) {
            this.partnerKey = partnerKey;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        @Override
        public String toString() {
            return "Wechat{" +
                    "appid='" + appid + '\'' +
                    ", partner='" + partner + '\'' +
                    ", partnerKey='" + partnerKey + '\'' +
                    ", notifyUrl='" + notifyUrl + '\'' +
                    '}';
        }
    }

    public static class Geetest{
        // geet验证-id
        private String id;
        // geet验证-key
        private String key;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "Geetest{" +
                    "id='" + id + '\'' +
                    ", key='" + key + '\'' +
                    '}';
        }
    }

}