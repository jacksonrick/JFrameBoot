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

}