package com.jf.system.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 系统自定义全局配置变量
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 15:52
 */
@Component
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
    public static String appkey;

    // 静态文件IP地址
    public static String static_host;

    // 静态文件目录,以 / 结尾
    public static String static_path;

    // 日志文件目录,以 / 结尾
    public static String log_path;

    // 支付宝-合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner;

    // 支付宝-APPID
    public static String appid;

    // 支付宝-账号
    public static String seller;

    // 支付宝-接收通知的接口
    public static String notify_url;

    // 支付宝-商户私钥,需要PKCS8格式,使用支付宝提供工具生成
    public static String rsa_private_key;

    // 支付宝-公钥，使用支付宝提供工具生成后上传至开发者平台=>点击查看支付宝公钥=>复制
    public static String alipay_public_key;

    // 微信-在开发平台登记的app应用
    public static String APP_ID;

    // 微信-商户号
    public static String mch_id;

    // 微信-商户在微信平台设置的32位长度的api秘钥
    public static String PARTNER_KEY;

    // 微信-异步通知地址
    public static String pay_notify_url;

    // geet验证-id
    public static String geetest_id;

    // geet验证-key
    public static String geetest_key;

    ////// 注意，Set方法必须为非静态方法，调用：SysConfig.name

    @Value("${system.appkey}")
    public void setAppkey(String appkey) {
        SysConfig.appkey = appkey;
    }

    @Value("${system.static_host}")
    public void setStatic_host(String static_host) {
        SysConfig.static_host = static_host;
    }

    @Value("${system.static_path}")
    public void setStatic_path(String static_path) {
        SysConfig.static_path = static_path;
    }

    @Value("${system.log_path}")
    public void setLog_path(String log_path) {
        SysConfig.log_path = log_path;
    }

    @Value("${system.aliyun.partner}")
    public void setPartner(String partner) {
        SysConfig.partner = partner;
    }

    @Value("${system.aliyun.app_id}")
    public void setAppid(String appid) {
        SysConfig.appid = appid;
    }

    @Value("${system.aliyun.seller}")
    public void setSeller(String seller) {
        SysConfig.seller = seller;
    }

    @Value("${system.aliyun.notify_url}")
    public void setNotify_url(String notify_url) {
        SysConfig.notify_url = notify_url;
    }

    @Value("${system.aliyun.rsa_private_key}")
    public void setRsa_private_key(String rsa_private_key) {
        SysConfig.rsa_private_key = rsa_private_key;
    }

    @Value("${system.aliyun.public_key}")
    public void setAlipay_public_key(String alipay_public_key) {
        SysConfig.alipay_public_key = alipay_public_key;
    }

    @Value("${system.wechat.pay_appid}")
    public void setAppId(String appId) {
        APP_ID = appId;
    }

    @Value("${system.wechat.pay_partner}")
    public void setMch_id(String mch_id) {
        SysConfig.mch_id = mch_id;
    }

    @Value("${system.wechat.partnerkey}")
    public void setPartnerKey(String partnerKey) {
        PARTNER_KEY = partnerKey;
    }

    @Value("${system.wechat.notify_url}")
    public void setPay_notify_url(String pay_notify_url) {
        SysConfig.pay_notify_url = pay_notify_url;
    }

    @Value("${system.geetest.geetest_id}")
    public void setGeetest_id(String geetest_id) {
        SysConfig.geetest_id = geetest_id;
    }

    @Value("${system.geetest.geetest_key}")
    public void setGeetest_key(String geetest_key) {
        SysConfig.geetest_key = geetest_key;
    }
}
