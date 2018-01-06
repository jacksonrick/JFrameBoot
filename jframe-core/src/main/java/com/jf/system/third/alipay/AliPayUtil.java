package com.jf.system.third.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jf.system.conf.SysConfig;

/**
 * 支付宝
 * Created on 16/6/20.
 */
public class AliPayUtil {

    /**
     * 下单
     *
     * @param body
     * @param subject
     * @param price
     * @param orderNum
     * @return
     */
    public static String alipay(String body, String subject, Double price, String orderNum) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                SysConfig.appid, SysConfig.rsa_private_key, "json", "utf-8", SysConfig.alipay_public_key, "RSA");

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(orderNum);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(price + "");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(SysConfig.notify_url);
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转账
     *
     * @param orderNum
     * @param price
     * @param account
     * @param realname
     * @return
     */
    public static String transfer(String orderNum, Double price, String account, String realname) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                SysConfig.appid, SysConfig.rsa_private_key, "json", "utf-8", SysConfig.alipay_public_key, "RSA");

        try {
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
            model.setOutBizNo(orderNum);
            model.setPayeeType("ALIPAY_LOGONID");
            model.setPayeeAccount(account);
            model.setPayeeRealName(realname);
            model.setAmount(price + "");
            model.setPayerShowName("");
            model.setRemark("提现到支付宝");
            request.setBizModel(model);
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("orderId:" + response.getOrderId());
                return response.getOrderId();
            } else {
                return null;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检查到账状态
     *
     * @param orderNum
     * @param thirdNum
     * @return
     */
    public static Boolean check(String orderNum, String thirdNum) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                SysConfig.appid, SysConfig.rsa_private_key, "json", "utf-8", SysConfig.alipay_public_key, "RSA");

        try {
            AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
            AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
            model.setOutBizNo(orderNum);
            model.setOrderId(thirdNum);
            request.setBizModel(model);
            AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return true;
            }
            return false;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 退款
     *
     * @param orderNum
     * @param thirdNum
     * @param price
     * @param reason
     * @return
     */
    public static String refund(String orderNum, String thirdNum, Double price, String reason) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                SysConfig.appid, SysConfig.rsa_private_key, "json", "utf-8", SysConfig.alipay_public_key, "RSA");

        try {
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(thirdNum);
            model.setTradeNo(orderNum);
            model.setRefundAmount(price + "");
            model.setRefundReason(reason);
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }


}
