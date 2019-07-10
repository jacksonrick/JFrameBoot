package com.jf.system.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jf.commons.LogManager;
import com.jf.system.conf.SysConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付宝
 * Created on 16/6/20.
 */
@Component
public class AliPayService {

    @Resource
    private SysConfig sysConfig;

    @Bean
    public AlipayClient alipayClient() {
        AlipayClient client = new DefaultAlipayClient(sysConfig.getAlipay().getGateway(), sysConfig.getAlipay().getAppId(),
                sysConfig.getAlipay().getRsaPrivateKey(), sysConfig.getAlipay().getFormat(), sysConfig.getAlipay().getCharset(),
                sysConfig.getAlipay().getPublicKey(), sysConfig.getAlipay().getSignType());
        return client;
    }

    @Resource
    private AlipayClient alipayClient;

    /**
     * 扫码支付
     *
     * @param subject
     * @param price
     * @param orderNum
     * @return URL
     */
    public String qrcode(String subject, Double price, String orderNum) throws Exception {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject(subject);
        model.setOutTradeNo(orderNum);
        model.setTotalAmount(price + "");
        model.setTimeoutExpress("30m");
        request.setBizModel(model);
        request.setNotifyUrl(sysConfig.getAlipay().getNotifyUrl()); // 额外参数，URL拼接参数，如biz=1
        AlipayResponse response = alipayClient.execute(request);
        JSONObject object = JSON.parseObject(response.getBody()).getJSONObject("alipay_trade_precreate_response");
        LogManager.info(object.toString(), AliPayService.class);
        if (object.get("value") != null) {
            return null;
        }
        String qr = object.getString("qr_code");
        return qr;
    }

    /**
     * Web下单
     *
     * @param body
     * @param subject
     * @param price
     * @param orderNum
     * @return Form
     */
    public String alipayWeb(String body, String subject, Double price, String orderNum) throws Exception {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(orderNum);
        model.setTimeoutExpress("10m");
        model.setTotalAmount(price + "");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(sysConfig.getAlipay().getNotifyUrl());
        request.setReturnUrl(sysConfig.getAlipay().getReturnUrl());
        AlipayResponse response = alipayClient.pageExecute(request);
        return response.getBody();
    }

    /**
     * Wap下单
     *
     * @param body
     * @param subject
     * @param price
     * @param orderNum
     * @return Form
     */
    public String alipayWap(String body, String subject, Double price, String orderNum) throws Exception {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(orderNum);
        model.setTimeoutExpress("10m");
        model.setTotalAmount(price + "");
        model.setProductCode("QUICK_WAP_WAY");
        request.setBizModel(model);
        request.setNotifyUrl(sysConfig.getAlipay().getNotifyUrl());
        request.setReturnUrl(sysConfig.getAlipay().getReturnUrl());
        AlipayResponse response = alipayClient.pageExecute(request);
        return response.getBody();
    }

    /**
     * APP下单
     *
     * @param body
     * @param subject
     * @param price
     * @param orderNum
     * @return URL参数对
     */
    public String alipayApp(String body, String subject, Double price, String orderNum) throws Exception {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(orderNum);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(price + "");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(sysConfig.getAlipay().getNotifyUrl());
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();
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
    public String transfer(String orderNum, Double price, String account, String realname) throws Exception {

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
            return response.getOrderId();
        } else {
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
    public Boolean check(String orderNum, String thirdNum) throws Exception {
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
    public String refund(String orderNum, String thirdNum, Double price, String reason) throws Exception {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(orderNum);
        model.setTradeNo(thirdNum);
        model.setRefundAmount(price + "");
        model.setRefundReason(reason);
        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        // value=10000 成功
        return response.getBody();
    }


}
