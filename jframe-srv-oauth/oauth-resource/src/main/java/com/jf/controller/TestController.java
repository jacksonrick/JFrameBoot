package com.jf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-06
 * Time: 14:59
 */
@RestController
public class TestController  {

    @RequestMapping("/")
    public String index() {
        System.out.println("oauth-resource-index");
        return "oauth-resource-index";
    }

    @RequestMapping("/login")
    public String login(String username, String password) {
        return "";
    }

    @RequestMapping("/monitor/a")
    public String monitor() {
        System.out.println("oauth-resource-monitor");
        return "oauth-resource-monitor";
    }

}
