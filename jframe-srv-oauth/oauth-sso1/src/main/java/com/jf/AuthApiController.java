package com.jf;

import com.jf.auth.AuthPassport;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-02
 * Time: 14:00
 */
@RestController
public class AuthApiController {

    @GetMapping("/index")
    @AuthPassport
    public Object index(String username, String type) {
        System.out.println("[index]当前用户：" + username);
        System.out.println("参数type: " + type);
        return "SUCCESS";
    }


}
