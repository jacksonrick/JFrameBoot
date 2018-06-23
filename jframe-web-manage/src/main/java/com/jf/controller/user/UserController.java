package com.jf.controller.user;

import com.github.pagehelper.PageInfo;
import com.jf.common.BaseController;
import com.jf.controller.view.UserExcel;
import com.jf.database.model.User;
import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.service.system.SystemService;
import com.jf.service.user.UserService;
import com.jf.system.annotation.AuthPassport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员模块
 *
 * @author rick
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private SystemService systemService;

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping("/userList")
    @AuthPassport(right = false)
    public String userList(String mode) {
        if (mode != null) { // 模态框
            return "user/user_list_mode";
        }
        return "user/user_list";
    }

    /**
     * 用户列表数据
     *
     * @param condition
     * @return
     */
    @RequestMapping("/userListData")
    @ResponseBody
    @AuthPassport
    public PageInfo userListData(User condition) {
        return userService.findUserByPage(condition);
    }

    /**
     * 禁用和启用用户
     *
     * @param userId
     * @return
     */
    @RequestMapping("/userEnable")
    @AuthPassport
    @ResponseBody
    public ResMsg userEnable(Long userId, HttpServletRequest request) {
        if (userId == null) {
            return new ResMsg(ResCode.CODE_22.code(), ResCode.CODE_22.msg());
        }
        if (userService.deleteUser(userId) > 0) {
            systemService.addAdminLog(request, "禁用/启用用户", "id=" + userId);
            return new ResMsg(ResCode.OPERATE_SUCCESS.code(), ResCode.OPERATE_SUCCESS.msg());
        }
        return new ResMsg(ResCode.OPERATE_FAIL.code(), ResCode.OPERATE_FAIL.msg());
    }

    /**
     * 会员编辑
     *
     * @param user model
     * @return
     */
    @RequestMapping("/userEdit")
    @AuthPassport
    @ResponseBody
    public ResMsg userEdit(@Valid User user, BindingResult br, HttpServletRequest request) {
        if (br.hasErrors()) {
            return new ResMsg(1, br.getFieldError().getDefaultMessage());
        }
        String nickname = user.getNickname();
        String phone = user.getPhone();
        String email = user.getEmail();
        Long userId = user.getId();
        if (userId != null) {
            User t = userService.findUserById(userId);
            if (!phone.equals(t.getPhone())) {
                if (userService.findUserCountByPhone(phone) > 0) {
                    return new ResMsg(3, "手机号已存在");
                }
            }
            if (!email.equals(t.getEmail())) {
                if (userService.findUserCountByEmail(email) > 0) {
                    return new ResMsg(4, "邮箱已存在");
                }
            }
            int res = userService.updateUser(user);
            if (res > 0) {
                systemService.addAdminLog(request, "编辑用户", "phone=" + phone);
                return new ResMsg(ResCode.UPDATE_SUCCESS.code(), ResCode.UPDATE_SUCCESS.msg());
            }
            return new ResMsg(ResCode.UPDATE_FAIL.code(), ResCode.UPDATE_FAIL.msg());
        }
        return new ResMsg(ResCode.CODE_22.code(), ResCode.CODE_22.msg());
    }

    /**
     * 用户详情
     *
     * @param userId
     * @param map
     * @return
     */
    @RequestMapping("/userDetail")
    @AuthPassport
    public String userDetail(Long userId, ModelMap map) {
        if (userId != null) {
            User user = userService.findUserById(userId);
            map.addAttribute("user", user);
        }
        return "user/user_edit";
    }

    /**
     * 导出用户信息Excel
     *
     * @param condition
     * @return
     */
    @RequestMapping("/exportUserExcel")
    @AuthPassport
    public ModelAndView exportUserExcel(User condition) {
        condition.setPage(false);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("list", userService.findByCondition(condition));
        return new ModelAndView(new UserExcel(), model);
    }

}
