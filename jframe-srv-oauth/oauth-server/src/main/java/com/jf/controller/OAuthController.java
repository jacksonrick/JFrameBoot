package com.jf.controller;

import com.jf.po.ICONSTANT;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * 认证控制器
 *
 * @author jackson-rick
 */
@RestController
public class OAuthController {

    /**
     * 认证页面
     *
     * @return
     */
    @GetMapping("/authentication/require")
    public ModelAndView require(@RequestParam(value = "error", required = false) String error, ModelMap map, HttpSession session) {
        if (error != null) {
            map.addAttribute("msg", "登录失败");
        }

        // 图形验证码
        session.setAttribute(ICONSTANT.SE_VERIFY, "1234");
        System.out.println("login page ----------------- 验证码为：1234");
        return new ModelAndView("login", map);
    }

    /**
     * 用户信息
     * <p>资源服务器保护</p>
     *
     * @param user
     * @return
     */
    @RequestMapping("/authentication/user")
    public Principal user(Principal user) {
        return user;
    }

    // form action "/oauth/authorize"
    /*@RequestMapping("/oauth/confirm_access")
    public String getAccessConfirmation(HttpServletRequest request) throws Exception {

        return "/user/oauth_approval";
    }*/

}
