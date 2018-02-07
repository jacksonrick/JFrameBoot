package com.jf.controller;

import com.jf.system.service.EmailService;
import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.service.user.UserService;
import com.jf.system.service.SMService;
import com.jf.system.annotation.Token;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by xujunfei on 2017/7/31.
 */
@RestController
public class TestController extends BaseController {

    @Resource
    private SMService smService;
    @Resource
    private EmailService emailService;

    @Resource
    private UserService userService;

    @GetMapping("/app/test")
    public ResMsg test() {
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/app/get")
    public ResMsg get() {
        Long userId = 10001l;
        String token = putUser(userId);
        return new ResMsg(0, SUCCESS, token);
    }

    @GetMapping("/app/token")
    public ResMsg token(String param, @Token Long userId) {
        System.out.println("/app/token param:" + param);
        System.out.println("/app/token userId:" + userId);
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/testSms")
    public ResMsg testSms() {
        smService.send("1", "17730215423");
        smService.send("2", "17730215423");
        smService.send("3", "17730215423");
        smService.send("4", "17730215423");
        smService.send("5", "17730215423");
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/testEmail")
    public ResMsg testEmail() {
        emailService.sendTemplateMail(null, "80222@qq.com", "123", "email_register");
        return new ResMsg(0, SUCCESS);
    }


    @GetMapping("/testSetSession")
    public ResMsg testSetSession(HttpSession session) {
        session.setAttribute("name", "xujunfei");
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/testGetSession")
    public ResMsg testGetSession(HttpSession session) {
        return new ResMsg(0, SUCCESS, session.getAttribute("name"));
    }

    @GetMapping("/testLock")
    public ResMsg testLock() {
        //userService.testLock(10000l);
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/testCache1")
    public ResMsg testCache1() {
        User user = userService.findUserById(10000l);
        return new ResMsg(0, SUCCESS, user);
    }

    @GetMapping("/testCache2")
    public ResMsg testCache2() {
        User user = new User(10000l);
        user.setMoney(1000d);
        userService.updateUser(user);
        return new ResMsg(0, SUCCESS);
    }

}
