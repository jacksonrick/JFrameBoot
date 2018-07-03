package com.jf;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-05-30
 * Time: 10:59
 */
@RestController
public class EndpointController {

    @RequestMapping("/auth/user")
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

}
