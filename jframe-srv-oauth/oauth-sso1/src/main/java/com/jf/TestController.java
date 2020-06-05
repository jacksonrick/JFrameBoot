package com.jf;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-11-01
 * Time: 10:49
 */
@Controller
public class TestController {

    // 默认首页
    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "index";
    }

    // 前后端分离时作为中间校验接口
    @RequestMapping("/sso")
    public String sso() {
        return "redirect:/static/test.html";
    }

    // 测试接口，不需要权限
    @RequestMapping("/test/a")
    @ResponseBody
    public String test() {
        System.out.println("test");
        return "test";
    }

    // 退出登录
    @RequestMapping("/sso/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    // 获取用户登陆信息，未登陆会跳转到SSO登陆页面
    @RequestMapping("/user")
    @ResponseBody
    public Authentication user(Authentication user) {
        System.out.println("user: " + user.getName());
        return user;
    }

}
