package com.jf;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-11-01
 * Time: 10:49
 */
@Controller
public class SsoController {

    // 默认首页
    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "index";
    }

    // 测试接口，不需要权限
    @RequestMapping("/test/get")
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

    // 获取token
    @RequestMapping("/token")
    @ResponseBody
    public Map<String, String> token(Authentication user) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) user.getDetails();
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", details.getTokenValue());
        return map;
    }

}
