package com.jf.system.third.wechat;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.jf.string.StringUtil;
import com.jf.system.conf.SysConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
        File file = new File(WxPayService.class.getResource("/apiclient_cert.p12").getFile());
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    // 以下通过sysconfig配置

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
    public Map<String, String> order_app(String orderNum, String body, Double orderPrice, String ip) {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", body);
        data.put("out_trade_no", orderNum);
        data.put("fee_type", "CNY");
        data.put("total_fee", (int) (orderPrice * 100) + "");
        data.put("spbill_create_ip", ip);
        data.put("notify_url", config.getWechat().getNotifyUrl());
        data.put("trade_type", "APP");
        //data.put("device_info", "");
        //data.put("product_id", "1");
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            if ("SUCCESS".equals(resp.get("result_code"))) {
                return resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public String order_qrcode(String orderNum, String body, Double orderPrice, String ip) {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", body);
        data.put("out_trade_no", orderNum);
        data.put("fee_type", "CNY");
        data.put("total_fee", (int) (orderPrice * 100) + "");
        data.put("spbill_create_ip", ip);
        data.put("notify_url", config.getWechat().getNotifyUrl());
        data.put("trade_type", "NATIVE");
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            if ("SUCCESS".equals(resp.get("result_code"))) {
                return resp.get("code_url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 公众号支付
     *
     * @param orderNum
     * @param body
     * @param orderPrice
     * @param ip
     * @param openid
     * @return
     */
    public Map<String, String> order_jsapi(String orderNum, String body, Double orderPrice, String ip, String openid) {
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
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            if ("SUCCESS".equals(resp.get("result_code"))) {
                return resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询订单
     *
     * @param outTradeNo
     * @return
     */
    public Map<String, String> queryOrder(String outTradeNo) {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        // 或 transaction_id
        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭订单
     *
     * @param outTradeNo
     * @return
     */
    public Map<String, String> closeOrder(String outTradeNo) {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        try {
            Map<String, String> resp = wxpay.closeOrder(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public Boolean refund(String orderNum, String refundNum, Double orderPrice, Double refundPrice) {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderNum);
        data.put("out_refund_no", refundNum);
        data.put("total_fee", (int) (orderPrice * 100) + "");
        data.put("refund_fee", (int) (refundPrice * 100) + "");
        try {
            Map<String, String> resp = wxpay.refund(data);
            System.out.println(resp);
            if ("SUCCESS".equals(resp.get("result_code"))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 查询退款
     *
     * @param outTradeNo
     * @return
     */
    public Map<String, String> queryRefund(String outTradeNo) {
        WXPay wxpay = new WXPay(this);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        // 或 out_refund_no transaction_id refund_id
        try {
            Map<String, String> resp = wxpay.refundQuery(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
