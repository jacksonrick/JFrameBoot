package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.service.user.UserService;
import com.jf.system.annotation.Token;
import com.jf.system.conf.SysConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-03-14
 * Time: 11:34
 */
@RestController
public class AppTestController extends BaseController {

    @Resource
    private UserService userService;

    @GetMapping("/app/test")
    public ResMsg test() {
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/app/get")
    public ResMsg get() {
        Long userId = 10001L;
        String token = bindToken(userId);
        return new ResMsg(0, SUCCESS, token);
    }

    @GetMapping("/app/token")
    public ResMsg token(String param, @Token(type = SysConfig.TOKEN_COOKIE, need = true) Long userId) {
        System.out.println("/app/token param:" + param);
        System.out.println("/app/token userId:" + userId);
        return new ResMsg(0, SUCCESS);
    }

}
