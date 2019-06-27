package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.commons.LogManager;
import com.jf.entity.ResMsg;
import com.jf.database.enums.ResCode;
import com.jf.service.UserService;
import com.jf.system.async.service.EmailService;
import com.jf.system.async.service.SMService;
import com.jf.system.conf.SysConfig;
import com.jf.system.third.jpush.JPushService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务测试
 * User: xujunfei
 * Date: 2018-03-14
 * Time: 11:35
 */
@Controller
public class TestController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("/testError")
    @ResponseBody
    public ResMsg testError() {
        userService.testRollback();
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    @RequestMapping("/testLog")
    @ResponseBody
    public ResMsg testLog() {
        LogManager.info("1");
        LogManager.info("2");
        LogManager.info("3");
        LogManager.info("4");
        LogManager.error("11");
        LogManager.error("22");
        LogManager.error("33");
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    /**
     * mysql cluster
     *
     * @return
     */
    @RequestMapping("/testMysqlCluster")
    @ResponseBody
    public ResMsg testMysqlCluster() {
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), userService.testMysqlCluster());
    }

    @RequestMapping("/transfer")
    @ResponseBody
    public ResMsg transfer() {
        int result = userService.transfer("100");
        System.out.println("result = " + result);
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    @Resource
    private SMService smService;
    @Resource
    private EmailService emailService;
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
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    @RequestMapping("/testEmail")
    @ResponseBody
    public ResMsg testEmail() {
        emailService.sendTemplateMail(null, "80222@qq.com", "123", "email_register");
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

   /* @RequestMapping("/htmltopdf")
    @ResponseBody
    public ResMsg htmltopdf() {
        try {
            // 导入数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "xujunfei");
            String str = pdfService.pdfCreate("pdf_user", map, sysConfig.getStaticPath());
            if ("error".equals(str)) {
                return new ResMsg(ResCode.FAIL.code(), ResCode.FAIL.msg());
            }
            return new ResMsg(1, str);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        }
    }*/

    @RequestMapping("/jpush")
    @ResponseBody
    public ResMsg jpush() {
        jPushService.sendPush("alert", "content", "10001");
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    @RequestMapping("/testAsyncRequest")
    public void testAsyncRequest(HttpServletRequest request) {
        AsyncContext context = request.startAsync();
        context.setTimeout(60000L);
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("over");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        });
        context.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("内部线程：" + Thread.currentThread().getName());

                    context.getResponse().setContentType("text/html;charset=UTF-8");
                    context.getResponse().getWriter().write("Hello");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                context.complete();
            }
        });
        System.out.println("主线程：" + Thread.currentThread().getName());
    }

}
