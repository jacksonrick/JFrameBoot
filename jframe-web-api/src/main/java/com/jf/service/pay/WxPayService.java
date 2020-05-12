package com.jf.service.pay;

import com.jf.commons.LogManager;
import com.jf.json.JacksonUtil;
import com.jf.sdk.wxpay.WXPay;
import com.jf.sdk.wxpay.WXPayConfig;
import com.jf.sdk.wxpay.WXPayUtil;
import com.jf.system.conf.SysConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 微信支付
 * User: xujunfei
 * Date: 2018-01-25
 * Time: 14:02
 */
@Component
public class WxPayService implements WXPayConfig {

    @Resource
    private SysConfig config;

    private byte[] certData;

    public WxPayService() throws Exception {
        // 证书读取 - 撤销、退款申请API中调用
        ClassPathResource resource = new ClassPathResource("apiclient_cert.p12");
        this.certData = FileCopyUtils.copyToByteArray(resource.getInputStream());
    }

    // 以下读取application.yml配置
    @Override
    public String getAppID() {
        return config.getWechat().getAppid();
    }

    @Override
    public String getMchID() {
        return config.getWechat().getPartner();
    }

    @Override
    public String getKey() {
        return config.getWechat().getPartnerKey();
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }


    /**
     * APP支付
     *
     * @param orderNum
     * @param body
     * @param orderPrice
     * @param ip
     * @return 微信返回的参数集
     */
    public Map<String, String> orderApp(String orderNum, String body, Double orderPrice, String ip) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", body);
        data.put("out_trade_no", orderNum);
        data.put("fee_type", "CNY");
        data.put("total_fee", (int) (orderPrice * 100) + "");
        data.put("spbill_create_ip", ip);
        data.put("notify_url", config.getWechat().getNotifyUrl());
        data.put("trade_type", "APP");
        // data.put("device_info", "");
        // data.put("product_id", "1");
        // data.put("attach", String.valueOf(type)); // 额外参数

        Map<String, String> resp = wxpay.unifiedOrder(data);
        LogManager.info(resp.toString());
        if ("SUCCESS".equals(resp.get("result_code"))) {
            // 二次签名
            Map<String, String> params = new HashMap<>();
            params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            params.put("appid", resp.get("appid"));
            params.put("noncestr", resp.get("nonce_str"));
            params.put("partnerid", resp.get("mch_id"));
            params.put("package", "Sign=WXPay");
            params.put("prepayid", resp.get("prepay_id"));
            params.put("sign", WXPayUtil.generateSignature(params, config.getWechat().getPartnerKey()));
            params.remove("package"); // 安卓解析问题
            LogManager.info(params.toString(), WxPayService.class);
            return params;
        }
        return null;
    }

    /**
     * 原生扫码支付
     *
     * @param orderNum
     * @param body
     * @param orderPrice
     * @param ip
     * @return 二维码地址
     */
    public String orderQrcode(String orderNum, String body, Double orderPrice, String ip) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", body);
        data.put("out_trade_no", orderNum);
        data.put("fee_type", "CNY");
        data.put("total_fee", (int) (orderPrice * 100) + "");
        data.put("spbill_create_ip", ip);
        data.put("notify_url", config.getWechat().getNotifyUrl());
        data.put("trade_type", "NATIVE");
        // data.put("attach", String.valueOf(type)); // 额外参数

        Map<String, String> resp = wxpay.unifiedOrder(data);
        LogManager.info(resp.toString());
        if ("SUCCESS".equals(resp.get("result_code"))) {
            return resp.get("code_url");
        }
        return "";
    }

    /**
     * 公众号支付
     * <p>注意：公众号支付需要商户平台绑定，appid是公众平台里的，key是商户平台里的</p>
     *
     * @param orderNum
     * @param body
     * @param orderPrice
     * @param ip
     * @param openid
     * @return
     */
    public Map<String, String> orderJsapi(String orderNum, String body, Double orderPrice, String ip, String openid) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", body);
        data.put("out_trade_no", orderNum);
        data.put("fee_type", "CNY");
        data.put("total_fee", (int) (orderPrice * 100) + "");
        data.put("spbill_create_ip", ip);
        data.put("notify_url", config.getWechat().getNotifyUrl());
        data.put("openid", openid);
        data.put("trade_type", "JSAPI");
        // data.put("attach", String.valueOf(type)); // 额外参数

        Map<String, String> resp = wxpay.unifiedOrder(data);
        LogManager.info(resp.toString());
        if ("SUCCESS".equals(resp.get("result_code"))) {
            // 二次签名
            Map<String, String> params = new HashMap<>();
            params.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            params.put("appId", resp.get("appid"));
            params.put("nonceStr", resp.get("nonce_str"));
            params.put("package", "prepay_id=" + resp.get("prepay_id"));
            params.put("signType", "MD5");
            params.put("paySign", WXPayUtil.generateSignature(params, config.getWechat().getPartnerKey()));
            LogManager.info(params.toString(), WxPayService.class);
            return params;
        }
        return null;
    }

    /**
     * 企业付款
     *
     * @param orderNum
     * @param openid
     * @param username
     * @param money
     * @param desc
     * @param ip
     * @return
     * @throws Exception
     */
    public String transfer(String orderNum, String openid, String username, Double money, String desc, String ip) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("partner_trade_no", orderNum);
        data.put("openid", openid);
        data.put("check_name", "FORCE_CHECK");
        data.put("re_user_name", username);
        data.put("amount", (int) (money * 100) + "");
        data.put("desc", desc);
        data.put("spbill_create_ip", ip);

        Map<String, String> resp = wxpay.transfer(data);
        LogManager.info(resp.toString(), WxPayService.class);
        if ("SUCCESS".equals(resp.get("result_code"))) {
            return JacksonUtil.objectToJson(resp);
        }
        return null;
    }

    /**
     * 查询订单
     *
     * @param outTradeNo
     * @return
     */
    public Map<String, String> queryOrder(String outTradeNo) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        // 或 transaction_id
        Map<String, String> resp = wxpay.orderQuery(data);
        LogManager.info(resp.toString());
        return resp;
    }

    /**
     * 关闭订单
     *
     * @param outTradeNo
     * @return
     */
    public Map<String, String> closeOrder(String outTradeNo) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        Map<String, String> resp = wxpay.closeOrder(data);
        LogManager.info(resp.toString());
        return resp;
    }

    /**
     * 退款
     *
     * @param orderNum
     * @param refundNum
     * @param orderPrice
     * @param refundPrice
     * @return 失败或成功
     */
    public Boolean refund(String orderNum, String refundNum, Double orderPrice, Double refundPrice) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderNum);
        data.put("out_refund_no", refundNum);
        data.put("total_fee", (int) (orderPrice * 100) + "");
        data.put("refund_fee", (int) (refundPrice * 100) + "");

        Map<String, String> resp = wxpay.refund(data);
        LogManager.info(resp.toString());
        if ("SUCCESS".equals(resp.get("result_code"))) {
            return true;
        }
        return false;
    }


    /**
     * 查询退款
     *
     * @param outTradeNo
     * @return
     */
    public Map<String, String> queryRefund(String outTradeNo) throws Exception {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        // 或 out_refund_no transaction_id refund_id

        Map<String, String> resp = wxpay.refundQuery(data);
        LogManager.info(resp.toString());
        return resp;
    }
}
