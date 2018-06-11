package com.jf.controller;

import com.jf.database.model.User;
import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.system.annotation.Login;
import com.jf.system.conf.SysConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping(path = {"/index", "/"})
    public String index() {
        return "index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResMsg test(String name, String type) {
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), name);
    }

    @RequestMapping("/testError")
    @ResponseBody
    public ResMsg testError() {
        System.out.println(1 / 0);
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    @RequestMapping("/user")
    @ResponseBody
    @Login
    public ResMsg user() {
        return new ResMsg(0, "user");
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResMsg login(HttpSession session) {
        User user = new User(10000l);
        user.setNickname("feifei");
        session.setAttribute(SysConfig.SESSION_USER, user);
        return new ResMsg(0, "SUCCESS", user);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ResMsg logout(HttpSession session) {
        session.removeAttribute(SysConfig.SESSION_USER);
        return new ResMsg(0, "SUCCESS", "logout");
    }
}
