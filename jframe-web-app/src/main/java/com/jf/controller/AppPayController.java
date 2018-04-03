package com.jf.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.jf.convert.Convert;
import com.jf.entity.ResMsg;
import com.jf.file.Qrcode;
import com.jf.json.JSONUtils;
import com.jf.string.StringUtil;
import com.jf.system.conf.SysConfig;
import com.jf.system.third.alipay.AliPayService;
import com.jf.system.third.wechat.WxPayService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付接口
 * Alipay & Wechat
 * Created by xujunfei on 2017/3/25.
 */
@Controller
public class AppPayController extends BaseController {

    @Resource
    private SysConfig config;
    @Resource
    private AliPayService aliPayService;
    @Resource
    private WxPayService wxPayService;

    //*******************************支付宝*******************************//

    @RequestMapping("/alipay_qr")
    public String alipay_qr() {
        return "pay/alipay_qr";
    }

    @RequestMapping("/alipay_qrcode")
    public void alipay_qrcode(HttpServletResponse response) throws Exception {
        String result = aliPayService.qrcode("商品", 0.01, StringUtil.getOrderCode());
        JSONObject object = JSONUtils.toJSONObject(result).getJSONObject("alipay_trade_precreate_response");
        if (object.get("code") != null) {
            System.out.println(object.get("sub_msg"));
            return;
        }
        String qr = object.getString("qr_code");
        // 生成二维码
        Qrcode.write(qr, response);
    }

    /**
     * 支付宝网页支付
     * <p>直接回跳转到支付宝网关</p>
     *
     * @return
     */
    @RequestMapping("/alipay")
    @ResponseBody
    public String alipay() throws Exception {
        String result = aliPayService.alipayWeb("测试", "商品", 100d, StringUtil.getOrderCode());
        System.out.println(result);
        return result;
    }

    /**
     * 支付宝退款
     *
     * @return
     */
    @RequestMapping("/ali_refund")
    @ResponseBody
    public ResMsg ali_refund() throws Exception {
        String result = aliPayService.refund("2018011711333198018150", "2018011721001004630200410967", 100d, "退款");
        return new ResMsg(0, SUCCESS, result);
    }

    /**
     * 网页支付成功跳转页面
     *
     * @return
     */
    @RequestMapping("/alipay_return")
    public String alipay_return() {
        return "pay/success";
    }


    /**
     * Alipay Callback
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/alipay_callback")
    public void alipay_callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("支付回调");
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决
            valueStr = new String(valueStr.getBytes(), "utf-8");
            params.put(name, valueStr);
        }
        boolean flag = AlipaySignature.rsaCheckV1(params, config.getAliyun().getPublicKey(),
                config.getAliyun().getCharset(), config.getAliyun().getSignType());
        System.out.println("param:" + params);
        System.out.println("flag:" + flag);
        if (flag) {
            String out_trade_no = request.getParameter("out_trade_no");
            String trade_status = request.getParameter("trade_status");
            String param = request.getParameter("passback_params");
            String tamount = request.getParameter("total_amount");
            int type = Convert.stringToInt(param, -1);
            double total_amount = Double.parseDouble(tamount);
            if (trade_status.equals("TRADE_SUCCESS")) {
                System.out.println("支付成功");
                output(response, "success");
                /*if (chargeService.callback(out_trade_no) == 0) {
                    log.info("###################wxpay_callback_shop charge_service callback success.");
                    output(response, "success");
                }*/
            } else {
                output(response, "failure");
            }
        } else {
            output(response, "failure");
        }
    }

    //*******************************微信*******************************//

    /**
     * 微信二维码支付页面
     *
     * @return
     */
    @RequestMapping("/wx_qr")
    public String wx_qr() {
        return "pay/wx_qr";
    }

    /**
     * 生成支付二维码
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/wx_qrcode")
    public void wx_qrcode(HttpServletResponse response) throws Exception {
        String code_url = wxPayService.order_qrcode(StringUtil.getOrderCode(), "商品", 0.01, "114.114.114.114");
        // 生成二维码
        Qrcode.write(code_url, response);
    }

    /**
     * 微信退款
     *
     * @return
     */
    @RequestMapping("/wx_refund")
    @ResponseBody
    public ResMsg wx_refund() throws Exception {
        wxPayService.refund("2018012515533680990396", StringUtil.getOrderCode(), 0.01, 0.01);
        return new ResMsg(0, SUCCESS);
    }

    /**
     * Wexin Callback
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/wxpay_callback")
    public void wxpay_callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("接收到微信支付回调");
        // 读取微信回调数据
        DataInputStream in = new DataInputStream(request.getInputStream());
        byte[] dataOrigin = new byte[request.getContentLength()];
        in.readFully(dataOrigin);
        if (null != in) in.close();

        String notifyData = new String(dataOrigin); // 支付结果通知的xml格式数据
        System.out.println("notifyData:" + notifyData);

        WXPay wxpay = new WXPay(wxPayService);
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);  // 转换成map
        if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
            System.out.println("签名正确");
            // 签名正确
            // 进行处理
            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
            output(response, output("SUCCESS", "OK"));
        } else {
            System.out.println("签名错误");
            output(response, output("FAIL", "FAIL"));
        }
    }

    /*@RequestMapping("/wxpay_callback_old")
    public void wxpay_callback_old(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建支付应答对象
        ResponseHandler resHandler = new ResponseHandler(request, response, config.getWechat().getPartner());
        // 判断签名
        if (resHandler.isTenpaySign()) {
            SortedMap<String, String> queryRes = resHandler.getAllParameters();
            String result_code = queryRes.get("result_code");
            if ("SUCCESS".equals(result_code)) {
                // 取结果参数做业务处理
                String out_trade_no = queryRes.get("out_trade_no");
                String attach = queryRes.get("attach");
                String tfee = queryRes.get("total_fee");
                int type = Convert.stringToInt(attach, -1);
                double total_fee = Double.parseDouble(tfee);
                *//*if (chargeService.callback(queryRes.get("out_trade_no")) == 0) {
                    log.info("###################wxpay_callback_shop charge_service callback success.");
                    resHandler.sendBack("SUCCESS");
                }*//*
            } else {
                resHandler.sendBack("FAIL");
            }
        } else {
            resHandler.sendBack("FAIL");
        }
    }*/

    @RequestMapping("/wx_login")
    @ResponseBody
    public ResMsg wx_login() {

        return new ResMsg(0, SUCCESS);
    }


    private void output(HttpServletResponse response, String ret) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(ret);
        out.flush();
        out.close();
    }

    private String output(String code, String message) {
        return "<xml> \n" +
                "  <return_code><![CDATA[" + code + "]]></return_code>\n" +
                "  <return_msg><![CDATA[" + message + "]]></return_msg>\n" +
                "</xml>";
    }
}
