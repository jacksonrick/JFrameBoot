package com.jf.system.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
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
    private SysConfig config;

    @Bean
    public AlipayClient alipayClient() {
        AlipayClient client = new DefaultAlipayClient(config.getAliyun().getGateway(), config.getAliyun().getAppId(),
                config.getAliyun().getRsaPrivateKey(), config.getAliyun().getFormat(), config.getAliyun().getCharset(),
                config.getAliyun().getPublicKey(), config.getAliyun().getSignType());
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
     * @return
     */
    public String qrcode(String subject, Double price, String orderNum) throws Exception {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject(subject);
        model.setOutTradeNo(orderNum);
        model.setTotalAmount(price + "");
        request.setBizModel(model);
        request.setNotifyUrl(config.getAliyun().getNotifyUrl());
        AlipayResponse response = alipayClient.execute(request);
        return response.getBody();
    }

    /**
     * Web下单
     *
     * @param body
     * @param subject
     * @param price
     * @param orderNum
     * @return
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
        request.setNotifyUrl(config.getAliyun().getNotifyUrl());
        request.setReturnUrl(config.getAliyun().getReturnUrl());
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
     * @return
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
        request.setNotifyUrl(config.getAliyun().getNotifyUrl());
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
        // code=10000 成功
        return response.getBody();
    }


}
