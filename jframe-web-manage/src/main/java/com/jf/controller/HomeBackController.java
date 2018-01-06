package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.Admin;
import com.jf.model.Module;
import com.jf.model.Msg;
import com.jf.service.system.AdminService;
import com.jf.service.system.ModuleService;
import com.jf.string.StringUtil;
import com.jf.system.SystemUtil;
import com.jf.system.annotation.AuthPassport;
import com.jf.system.conf.SysConfig;
import com.jf.system.util.VERSION;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 管理中心模块
 *
 * @author rick
 */
@Controller
@RequestMapping("/admin")
public class HomeBackController extends BaseController {

    @Resource
    private AdminService adminService;
    @Resource
    private ModuleService moduleService;

    /**
     * 登录页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, ModelMap map) {
        if (getAdmin(request) != null) {
            return "redirect:index";
        }
        map.put("version", VERSION.SYS_VER);
        return "login";
    }

    /**
     * 登录
     *
     * @param validNum
     * @param username
     * @param password
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/dologin")
    @ResponseBody
    public ResMsg dologin(String validNum, String username, String password, HttpSession session,
                          HttpServletRequest request) {
        if (StringUtil.isBlank(validNum)) {
            return new ResMsg(1, "请输入验证码");
        }
        // 验证码验证
        /*String sessionCode = (String) session.getAttribute(VERSION.SESSION_RAND);
        if (sessionCode == null || !sessionCode.equals(validNum)) {
            return new ResMsg(2, "验证码错误");
        }*/
        if (StringUtil.isBlank(username)) {
            return new ResMsg(3, "用户名不能为空 ");
        }
        if (StringUtil.isBlank(password)) {
            return new ResMsg(4, "密码不能为空");
        }
        Admin admin = adminService.login(username, password, request.getRemoteAddr());
        if (admin == null) {
            return new ResMsg(5, "用户名或密码不正确");
        }
        if (admin.getRole().getIsDelete()) {
            return new ResMsg(6, "当前组已被禁用");
        }
        if (admin.getIsDelete()) {
            return new ResMsg(7, "账户已被冻结");
        }
        session.setAttribute(SysConfig.SESSION_ADMIN, admin);
        session.removeAttribute(SysConfig.SESSION_RAND);
        addAdminLog(request, "管理员登录", "username=" + username);
        return new ResMsg(0, "登录成功");
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SysConfig.SESSION_ADMIN);
        return "redirect:login";
    }

    // --------------以上请求不拦截-------------- //

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/index")
    @AuthPassport(right = false)
    public String index(ModelMap map, HttpServletRequest request) {
        Admin admin = getAdmin(request);
        map.put("admin", admin);
        List<Module> list = moduleService.findModuleByRole(admin.getRole().getId(), false);
        map.addAttribute("modules", list);
        List<Msg> msgs = adminService.findByTodayMsg(admin.getId());
        map.addAttribute("msgs", msgs);
        map.addAttribute("size", msgs.size());
        map.put("version", VERSION.SYS_VER);
        map.put("upgrade", VERSION.UPGRADE);
        return "index";
    }

    /**
     * home页面
     *
     * @return
     */
    @RequestMapping("/home")
    @AuthPassport(right = false)
    public String home(ModelMap map, HttpServletRequest request) {
        map.put("date", new Date().getTime());
        map.put("arr", new SystemUtil().getSpace());
        map.put("sysname", VERSION.SYS_NAME);
        map.put("version", VERSION.SYS_VER);
        map.put("info", request.getSession().getServletContext().getServerInfo());
        return "home/home";
    }

    /**
     * 修改密码页面
     *
     * @return
     */
    @RequestMapping("/pwd")
    @AuthPassport(right = false)
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
    @AuthPassport(right = false)
    @ResponseBody
    public ResMsg changePwd(String oldPwd, String newPass, String newPass2, HttpServletRequest request) {
        Admin admin = getAdmin(request);
        if (StringUtil.isBlank(oldPwd)) {
            return new ResMsg(1, "旧密码不能为空");
        }
        if (StringUtil.isBlank(newPass)) {
            return new ResMsg(2, "新密码不能为空");
        }
        if (StringUtil.isBlank(newPass2)) {
            return new ResMsg(3, "重复密码不能为空");
        }
        if (!newPass.equals(newPass2)) {
            return new ResMsg(4, "两次输入的密码不一样");
        }
        Admin adm = adminService.findAdminByNameAndPwd(admin.getAdminName(), oldPwd);
        if (adm == null) {
            return new ResMsg(5, "旧密码错误");
        }
        adminService.updatePassword(adm.getId(), newPass);
        return new ResMsg(0, UPDATE_SUCCESS);
    }

}
