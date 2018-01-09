package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.service.user.UserService;
import com.jf.string.StringUtil;
import com.jf.system.annotation.AuthPassport;
import com.jf.system.annotation.Login;
import com.jf.system.conf.SysConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by xujunfei on 2017/6/22.
 */
@Controller
@RequestMapping("/shop")
public class HomeController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 登录页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        if (getUser(request) != null) {
            return "redirect:index";
        }
        return "login";
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/dologin")
    @ResponseBody
    public ResMsg dologin(String username, String password, HttpSession session, HttpServletRequest request) {
        User user = userService.findUserById(10000l);
        session.setAttribute(SysConfig.SESSION_USER, user);
        return new ResMsg(0, "登录成功，正在跳转");
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SysConfig.SESSION_USER);
        return "redirect:login";
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/index")
    @Login
    public String index(ModelMap map, HttpServletRequest request) {
        User user = getUser(request);
        System.out.println(user.getId());
        return "index";
    }

    /**
     * home页面
     *
     * @return
     */
    @RequestMapping("/home")
    @Login
    public String home(ModelMap map, HttpServletRequest request) {
        System.out.println(SUCCESS);
        return "home/home";
    }

    /**
     * 修改密码页面
     *
     * @return
     */
    @RequestMapping("/pwd")
    @Login
    public String pwd() {
        return "home/pwd";
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPass
     * @param newPass2
     * @return
     */
    @RequestMapping("/changePwd")
    @Login
    @ResponseBody
    public ResMsg changePwd(String oldPwd, String newPass, String newPass2, HttpServletRequest request) {

        return new ResMsg(0, UPDATE_SUCCESS);
    }

}
