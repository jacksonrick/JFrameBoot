package com.jf.controller;

import com.jf.annotation.Token;
import com.jf.common.BaseController;
import com.jf.entity.ResMsg;
import com.jf.service.UserService;
import com.jf.system.conf.IConstant;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by xujunfei on 2017/4/7.
 */
@RestController
@RequestMapping("/app")
public class IndexAppController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("/findUser")
    @ResponseBody
    @Token
    public ResMsg findUser(Integer id) {
        System.out.println(id);
        userService.findUserById(id);
        return ResMsg.success();
    }

    @GetMapping("/home")
    public ResMsg home() {
        return ResMsg.success();
    }

    @GetMapping("/online")
    public ResMsg online() {
        return ResMsg.successdata(true);
    }

    @GetMapping("/test")
    public ResMsg test() {
        String a = null;
        System.out.println(a.length());
        return ResMsg.success();
    }

    @GetMapping("/get")
    public ResMsg get() {
        Integer userId = 10000;
        String token = bindToken(userId);
        return ResMsg.successdata(token);
    }

    /**
     * Token测试
     * *注意：第一个参数必须要是 `Integer param_name`
     *
     * @param userId
     * @param param
     * @return
     */
    @GetMapping("/token")
    @Token(type = IConstant.TOKEN_HEADER)
    public ResMsg token(Integer userId, String param) {
        System.out.println("/app/token param:" + param);
        System.out.println("/app/token userId:" + userId);
        return ResMsg.successdata(userId);
    }

    @GetMapping("/token2")
    @Token(type = IConstant.TOKEN_COOKIE, need = false)
    public ResMsg token2(Integer userId, String param) {
        System.out.println("/app/token param:" + param);
        System.out.println("/app/token userId:" + userId);
        return ResMsg.successdata(userId);
    }

    @GetMapping("/swagger")
    @ApiOperation(value = "swagger测试", notes = "详细描述")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "p1", value = "参数1", dataType = "String", defaultValue = "haha"),
            @ApiImplicitParam(name = "s2", value = "参数2", dataType = "Integer", defaultValue = "99")
    })
    public ResMsg swagger(String p1, Integer s2) {
        return ResMsg.success();
    }
}
