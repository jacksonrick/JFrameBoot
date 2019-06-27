package com.jf.controller;

import com.jf.annotation.Token;
import com.jf.common.BaseController;
import com.jf.commons.LogManager;
import com.jf.database.model.User;
import com.jf.entity.ResMsg;
import com.jf.database.enums.ResCode;
import com.jf.service.CommonService;
import com.jf.service.UserService;
import com.jf.string.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * App通用接口
 * Created by xujunfei on 2017/4/7.
 */
@RestController
@RequestMapping("/app")
public class AppController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private CommonService commonService;

    @GetMapping("/")
    public ResMsg index() {
        return ResMsg.success();
    }

    @GetMapping("/online")
    public ResMsg online() {
        return ResMsg.successdata(true);
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param codeType
     * @return
     */
    @PostMapping("/sms")
    @Token(need = false)
    public ResMsg sms(Long userId, String phone, String codeType) {
        if (StringUtil.isBlank(phone)) {
            return ResMsg.fail("手机号不能为空");
        }
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();
        if (!isMatch) {
            return ResMsg.fail("手机号格式错误");
        }

        if (StringUtil.isBlank(codeType)) {
            return ResMsg.fail("验证码类型错误");
        }
        if (commonService.findCountByPhone(phone) > 0) {
            return ResMsg.fail("发送过于频繁，请1分钟后重试");
        }

        // 判断发送类型
        if ("bind".equals(codeType)) {
            if (userId == null) {
                return ResMsg.fail(ResCode.NO_LOGIN.code(), "请先登陆");
            }
            Long id = userService.findIdByPhone(phone);
            if (id != null) {
                if (id.equals(userId)) {
                    return ResMsg.fail(10, "你已经绑定该手机号");
                } else {
                    return ResMsg.fail(11, "手机号已被绑定");
                }
            }
        } else if ("pwd".equals(codeType)) {
            int count = userService.findCountByPhone(phone);
            if (count < 1) {
                return ResMsg.fail(12, "手机号未注册");
            }
        } else if ("reg".equals(codeType)) {
            int count = userService.findCountByPhone(phone);
            if (count > 0) {
                return ResMsg.fail(13, "手机号已注册");
            }
        } else {
            return ResMsg.fail("验证码类型错误");
        }

        // 先获取有效期内的验证码，没有则重新生成
        String sms = commonService.findValidCode(phone);
        if (StringUtil.isBlank(sms)) {
            sms = StringUtil.getSmsCode(6);
        }
        // String sms = "123456";
        if (commonService.insertSMS(phone, sms, codeType) > 0) {
            //smService.sendSms(sms, phone);
            LogManager.info(phone + ",验证码为:" + sms);
            return ResMsg.success();
        }
        return ResMsg.fail();
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param sms
     * @return
     */
    @PostMapping("/register")
    public ResMsg register(String phone, String password, String sms) {
        if (StringUtil.isBlank(phone)) {
            return ResMsg.fail("手机号不能为空");
        }
        if (StringUtil.isBlank(password)) {
            return ResMsg.fail("密码不能为空");
        }
        if (StringUtil.isBlank(sms)) {
            return ResMsg.fail("验证码不能为空");
        }
        // 验证码校验
        if (!commonService.checkSmsCode(phone, sms)) {
            return ResMsg.fail("验证码错误");
        }
        if (userService.findCountByPhone(phone) > 0) {
            return ResMsg.fail(13, "手机号已被注册");
        }
        User user = User.Builder.anUser().phone(phone).password(password).build();
        String token = userService.register(user);
        if (StringUtil.isBlank(token)) {
            return ResMsg.fail("注册成功");
        }
        return ResMsg.successdata(token);
    }

    /**
     * 手机号登陆
     *
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResMsg login(String phone, String password) {
        if (StringUtil.isBlank(phone)) {
            return ResMsg.fail("请输入手机号");
        }
        if (StringUtil.isBlank(password)) {
            return ResMsg.fail("请输入密码");
        }
        return userService.login(phone, password);
    }

    /**
     * 退出登陆
     *
     * @param loginId
     * @return
     */
    @PostMapping("/logout")
    @Token
    public ResMsg logout(Long userId) {
        if (userService.logout(userId) > 0) {
            return ResMsg.success();
        }
        return ResMsg.fail();
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPwd
     * @param password
     * @return
     */
    @PostMapping("/pwd/edit")
    @Token
    public ResMsg pwdEdit(Long userId, String oldPwd, String password) {
        if (StringUtil.isBlank(oldPwd)) {
            return ResMsg.fail("请输入旧密码");
        }
        if (StringUtil.isBlank(password)) {
            return ResMsg.fail("请输入新密码");
        }
        int count = userService.findCountByIdAndPwd(userId, oldPwd);
        if (count < 1) {
            return ResMsg.fail("旧密码错误");
        }
        User u = User.Builder.anUser().id(userId).password(password).build();
        if (userService.updateUserInfo(u) > 0) {
            return ResMsg.success("修改成功");
        }
        return ResMsg.fail("修改失败");
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @param sms
     * @return
     */
    @PostMapping("/pwd/forget")
    public ResMsg pwdForget(String phone, String password, String sms) {
        if (StringUtil.isBlank(phone)) {
            return ResMsg.fail("请输入手机号");
        }
        if (StringUtil.isBlank(password)) {
            return ResMsg.fail("请输入新密码");
        }
        if (StringUtil.isBlank(sms)) {
            return ResMsg.fail("请输入短信验证码");
        }
        if (!commonService.checkSmsCode(phone, sms)) {
            return ResMsg.fail("验证码错误");
        }
        Long id = userService.findIdByPhone(phone);
        if (id == null) {
            return ResMsg.fail("手机号不存在");
        }
        User u = User.Builder.anUser().id(id).password(password).build();
        if (userService.updateUserInfo(u) > 0) {
            return ResMsg.success("修改成功");
        }
        return ResMsg.fail("修改失败");
    }

    /**
     * 绑定新手机号
     *
     * @param userId
     * @param phone
     * @param sms
     * @return
     */
    @PostMapping("/phone/bind")
    @Token
    public ResMsg phoneBind(Long userId, String phone, String sms) {
        if (StringUtil.isBlank(phone)) {
            return ResMsg.fail("请输入手机号");
        }
        if (StringUtil.isBlank(sms)) {
            return ResMsg.fail("请输入短信验证码");
        }
        if (!commonService.checkSmsCode(phone, sms)) {
            return ResMsg.fail("验证码错误");
        }
        User u = User.Builder.anUser().id(userId).phone(phone).build();
        if (userService.updateUserInfo(u) > 0) {
            return ResMsg.success("修改成功");
        }
        return ResMsg.fail("修改失败");
    }

}
