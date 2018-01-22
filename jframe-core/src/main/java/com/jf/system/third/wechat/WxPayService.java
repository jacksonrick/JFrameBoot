package com.jf.system.third.wechat;

import com.alibaba.fastjson.JSON;
import com.jf.system.LogManager;
import com.jf.system.conf.SysConfig;
import com.wechat.WXUtil;
import com.wechat.util.HttpUtils;
import com.wechat.util.XMLUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信
 * Created on 16/6/29.
 */
@Component
public class WxPayService {

    @Resource
    private SysConfig config;

    /**
     * APP支付
     *
     * @param ip       IP地址
     * @param body
     * @param price    价格
     * @param orderNum 订单编号
     * @return
     */
    public String wxPayApp(String ip, String body, Double price, String orderNum) {
        //Map<Object, Object> resInfo = new HashMap<Object, Object>();
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        int retcode;
        String retmsg = "";
        String xml_body = "";

        //获取token值
        //String token = AccessTokenRequestHandler.getAccessToken();

        //设置package订单参数
        //packageReqHandler.setParameter("bank_type", "WX");//银行渠道
        parameters.put("body", body); //商品描述
        parameters.put("notify_url", config.getWechat().getNotifyUrl()); //接收财付通通知的URL
        parameters.put("mch_id", config.getWechat().getPartner()); //商户号
        parameters.put("out_trade_no", orderNum); //商家订单号+随机数 = 支付流水号
        parameters.put("total_fee", (int) (price * 100) + ""); //商品金额,以分为单位
        //parameters.put("spbill_create_ip",HttpUtils.getIpAddr(request)); //订单生成的机器IP，指用户浏览器端IP
        parameters.put("spbill_create_ip", ip); //订单生成的机器IP，指用户浏览器端IP
        //parameters.put("fee_type", "1"); //币种，1人民币   66
        parameters.put("trade_type", "APP"); //字符编码
        // 随机字符串
        String noncestr = WXUtil.getNonceStr();
        ////设置获取prepayid支付参数
        parameters.put("appid", config.getWechat().getAppid());
        parameters.put("nonce_str", noncestr);
        //生成获取预支付签名
        String sign = WXUtil.createSign(parameters, config.getWechat().getPartner());
        //增加非参与签名的额外参数
        parameters.put("sign", sign);
        //resInfo.put("retmsg", WXUtil.getXmlBody(parameters));

        String xml = WXUtil.getXmlBody(parameters);
        byte[] bytes = new byte[0];
        String s = null;
        try {
            // 请求微信支付网关
            bytes = HttpUtils.postXml("https://api.mch.weixin.qq.com/pay/unifiedorder", xml, "UTF-8");
            s = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SortedMap<Object, Object> finalpackage = new TreeMap<Object, Object>();
        String timestamp = WXUtil.getTimeStamp();
        finalpackage.put("appid", config.getWechat().getAppid());
        finalpackage.put("timestamp", timestamp);
        finalpackage.put("noncestr", noncestr);
        finalpackage.put("partnerid", config.getWechat().getPartner());
        finalpackage.put("package", "Sign=WXPay");
        try {
            Map map = XMLUtil.doXMLParse(s);
            finalpackage.put("prepayid", map.get("prepay_id"));
            if (map.get("return_code").equals("FAIL")) {
                LogManager.error(map.toString(), WxPayService.class);
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String finalsign = WXUtil.createSign(finalpackage, config.getWechat().getPartner());
        finalpackage.put("sign", finalsign);

        return JSON.toJSONString(finalpackage);
    }

    public String wxRefund(String out_trade_no, String refund_id, Double price, String orderNum) {

        return "";
    }

    // 公众号支付需要使用WeixinJSBridge
}
