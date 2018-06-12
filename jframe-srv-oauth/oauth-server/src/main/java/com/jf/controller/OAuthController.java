package com.jf.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView require(@RequestParam(value = "error", required = false) String error, ModelMap map) {
        if (error != null) {
            map.addAttribute("msg", "不正确的用户名或密码");
        }
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

}
