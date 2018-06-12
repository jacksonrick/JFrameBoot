package com.jf.controller;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-06
 * Time: 14:59
 */
@RestController
@EnableResourceServer
public class TestController {

    @RequestMapping("/")
    public String index() {
        return "oauth-resource";
    }

}
