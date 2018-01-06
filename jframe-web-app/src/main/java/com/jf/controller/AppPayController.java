package com.jf.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.jf.convert.Convert;
import com.jf.system.conf.SysConfig;
import com.jf.system.third.wechat.ResponseHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

/**
 * APP支付接口回调
 * Alipay & Wechat
 * Created by xujunfei on 2017/3/25.
 */
//@Controller
//@RequestMapping("/app")
public class AppPayController {

    /**
     * Alipay Callback
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/alipay_callback")
    public void alipay_callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean flag = AlipaySignature.rsaCheckV1(params, SysConfig.alipay_public_key, "utf-8", "RSA");
        if (flag) {
            String out_trade_no = request.getParameter("out_trade_no");
            String trade_status = request.getParameter("trade_status");
            String param = request.getParameter("passback_params");
            String tamount = request.getParameter("total_amount");
            int type = Convert.stringToInt(param, -1);
            double total_amount = Double.parseDouble(tamount);
            if (trade_status.equals("TRADE_SUCCESS")) {
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

    /**
     * Wechat Callback
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/wxpay_callback")
    public void wxpay_callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建支付应答对象
        ResponseHandler resHandler = new ResponseHandler(request, response);
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
                /*if (chargeService.callback(queryRes.get("out_trade_no")) == 0) {
                    log.info("###################wxpay_callback_shop charge_service callback success.");
                    resHandler.sendBack("SUCCESS");
                }*/
            } else {
                resHandler.sendBack("FAIL");
            }
        } else {
            resHandler.sendBack("FAIL");
        }
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
}
