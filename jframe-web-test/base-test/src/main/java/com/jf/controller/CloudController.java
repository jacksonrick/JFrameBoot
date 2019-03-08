package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.restapi.OrderRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 13:59
 */
@Controller
@RefreshScope // SpringCloud Config Refresh
public class CloudController extends BaseController {

    @Resource
    private OrderRestService orderRestService;

    @Value("${s1:0}")
    public String a;

    /**
     * ↑↓ SpringCloud Config
     *
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public ResMsg test() {
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), a);
    }

    /**
     * 测试服务消费-Feign
     *
     * @return
     */
    @RequestMapping("/order")
    @ResponseBody
    public String order() {
        return orderRestService.order(10000, 100);
    }
}
