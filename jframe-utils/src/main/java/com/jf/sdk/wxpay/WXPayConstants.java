package com.jf.sdk.wxpay;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-02-25
 * Time: 10:23
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String MICROPAY_URL     = "https://api.mch.weixin.qq.com/pay/micropay";
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String ORDERQUERY_URL   = "https://api.mch.weixin.qq.com/pay/orderquery";
    public static final String REVERSE_URL      = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
    public static final String CLOSEORDER_URL   = "https://api.mch.weixin.qq.com/pay/closeorder";
    public static final String REFUND_URL       = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    public static final String REFUNDQUERY_URL  = "https://api.mch.weixin.qq.com/pay/refundquery";
    public static final String DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    public static final String REPORT_URL       = "https://api.mch.weixin.qq.com/payitil/report";
    public static final String SHORTURL_URL     = "https://api.mch.weixin.qq.com/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/tools/authcodetoopenid";
    public static final String TRANSFER_URL     = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL     = "https://api.mch.weixin.qq.com/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL   = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL      = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL   = "https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL       = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL  = "https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL       = "https://api.mch.weixin.qq.com/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL     = "https://api.mch.weixin.qq.com/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/sandboxnew/tools/authcodetoopenid";

}
