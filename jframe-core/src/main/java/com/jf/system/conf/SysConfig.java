package com.jf.system.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description: 系统自定义全局配置变量 application.yml
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 15:52
 */
@Configuration
@ConfigurationProperties(prefix = "system")
public class SysConfig {

    // version
    private String version;
    // 是否开发环境
    private Boolean dev;
    // app key
    private String appkey;
    // 静态文件IP地址
    private String staticHost;
    // 静态文件目录,以 / 结尾
    private String staticPath;
    // 日志文件目录,以 / 结尾
    private String logPath;
    // serverId
    private String serverId;
    // FastDFS Nginx服务器地址,以 / 结尾
    private String fdfsNginx;

    // 支付宝
    private SysConfig.Aliyun aliyun;
    // 微信
    private SysConfig.Wechat wechat;
    // 极验验证
    private SysConfig.Geetest geetest;
    // jpush推送
    private SysConfig.Jpush jpush;
    // 上传
    private SysConfig.Upload upload;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean dev() {
        return dev;
    }

    public void setDev(Boolean dev) {
        this.dev = dev;
    }

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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getFdfsNginx() {
        return fdfsNginx;
    }

    public void setFdfsNginx(String fdfsNginx) {
        this.fdfsNginx = fdfsNginx;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "version='" + version + '\'' +
                ", dev='" + dev + '\'' +
                ", appkey='" + appkey + '\'' +
                ", staticHost='" + staticHost + '\'' +
                ", staticPath='" + staticPath + '\'' +
                ", logPath='" + logPath + '\'' +
                ", serverId='" + serverId + '\'' +
                ", fdfsNginx='" + fdfsNginx + '\'' +
                ", aliyun=" + aliyun +
                ", wechat=" + wechat +
                ", geetest=" + geetest +
                ", jpush=" + jpush +
                ", upload=" + upload +
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

    public SysConfig.Jpush getJpush() {
        return jpush;
    }

    public void setJpush(SysConfig.Jpush jpush) {
        this.jpush = jpush;
    }

    public SysConfig.Upload getUpload() {
        return upload;
    }

    public void setUpload(SysConfig.Upload upload) {
        this.upload = upload;
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
        // 支付宝-通知页面跳转
        private String returnUrl;
        // 支付宝-商户私钥,需要PKCS8格式,使用支付宝提供工具生成
        private String rsaPrivateKey;
        // 支付宝-公钥，使用支付宝提供工具生成后上传至开发者平台=>点击查看支付宝公钥=>复制
        private String publicKey;
        // 支付宝网关-生产环境 | 沙箱环境
        private String gateway;

        private String charset = "utf-8";
        private String signType = "RSA2";
        private String format = "json";

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

        public String getReturnUrl() {
            return returnUrl;
        }

        public void setReturnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
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

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        @Override
        public String toString() {
            return "Aliyun{" +
                    "partner='" + partner + '\'' +
                    ", appId='" + appId + '\'' +
                    ", seller='" + seller + '\'' +
                    ", notifyUrl='" + notifyUrl + '\'' +
                    ", returnUrl='" + returnUrl + '\'' +
                    ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                    ", publicKey='" + publicKey + '\'' +
                    ", gateway='" + gateway + '\'' +
                    '}';
        }
    }

    public static class Wechat {
        // 微信-在开发平台登记的app应用
        private String appid;
        // 微信-商户号、MCH_ID
        private String partner;
        // 微信-商户在微信平台设置的32位长度的api秘钥、API_KEY
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

    public static class Geetest {
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

    public static class Jpush {
        private String appkey;
        private String secret;
        private Boolean iosProduct; // IOS开发环境-false，生产环境-true

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public Boolean getIosProduct() {
            return iosProduct;
        }

        public void setIosProduct(Boolean iosProduct) {
            this.iosProduct = iosProduct;
        }

        @Override
        public String toString() {
            return "Jpush{" +
                    "appkey='" + appkey + '\'' +
                    ", secret='" + secret + '\'' +
                    ", iosProduct='" + iosProduct + '\'' +
                    '}';
        }
    }

    public static class Upload {
        // fdfs enable，如果为false,则启用spring上传到本地指定目录
        private Boolean fdfs;
        // 图片大小
        private Integer imgSize;
        // 图片类型
        private String[] imgType;
        // 文件大小【非图片】
        private Integer fileSize;
        // 文件类型
        private String[] fileType;

        public Boolean getFdfs() {
            return fdfs;
        }

        public void setFdfs(Boolean fdfs) {
            this.fdfs = fdfs;
        }

        public Integer getImgSize() {
            return imgSize;
        }

        public void setImgSize(Integer imgSize) {
            this.imgSize = imgSize;
        }

        public String[] getImgType() {
            return imgType;
        }

        public void setImgType(String[] imgType) {
            this.imgType = imgType;
        }

        public Integer getFileSize() {
            return fileSize;
        }

        public void setFileSize(Integer fileSize) {
            this.fileSize = fileSize;
        }

        public String[] getFileType() {
            return fileType;
        }

        public void setFileType(String[] fileType) {
            this.fileType = fileType;
        }

        @Override
        public String toString() {
            return "Upload{" +
                    "fdfs='" + fdfs + '\'' +
                    ", imgSize=" + imgSize +
                    ", imgType=" + Arrays.toString(imgType) +
                    ", fileSize=" + fileSize +
                    ", fileType=" + Arrays.toString(fileType) +
                    '}';
        }
    }

}