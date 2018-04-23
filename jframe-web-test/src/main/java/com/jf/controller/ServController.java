package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.system.conf.SysConfig;
import com.jf.system.service.EmailService;
import com.jf.system.service.PDFService;
import com.jf.system.service.SMService;
import com.jf.system.third.jpush.JPushService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-03-14
 * Time: 11:36
 */
@Controller
public class ServController extends BaseController {

    @Resource
    private SMService smService;
    @Resource
    private EmailService emailService;
    @Resource
    private PDFService pdfService;
    @Resource
    private JPushService jPushService;

    @Resource
    private SysConfig sysConfig;

    @RequestMapping("/testSms")
    @ResponseBody
    public ResMsg testSms() {
        smService.send("1", "17730215423");
        smService.send("2", "17730215423");
        smService.send("3", "17730215423");
        smService.send("4", "17730215423");
        smService.send("5", "17730215423");
        return new ResMsg(0, SUCCESS);
    }

    @RequestMapping("/testEmail")
    @ResponseBody
    public ResMsg testEmail() {
        emailService.sendTemplateMail(null, "80222@qq.com", "123", "email_register");
        return new ResMsg(0, SUCCESS);
    }

    @RequestMapping("/htmltopdf")
    @ResponseBody
    public ResMsg htmltopdf() {
        try {
            // 导入数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "xujunfei");
            String str = pdfService.pdfCreate("pdf_user", map, sysConfig.getStaticPath());
            if ("error".equals(str)) {
                return new ResMsg(0, "转换失败");
            }
            return new ResMsg(1, str);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(0, "error");
        }
    }

    @RequestMapping("/jpush")
    @ResponseBody
    public ResMsg jpush() {
        jPushService.sendPush(1, "hello", null, "10001");
        return new ResMsg(0, SUCCESS);
    }

}
