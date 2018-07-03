package com.jf.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-05-28
 * Time: 15:35
 */
@RestController
public class MyEndpoints {

    // 不做安全控制
    @RequestMapping("/api/a/1")
    @ResponseBody
    public String api1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "api1";
    }

    // 安全控制
    @RequestMapping("/api/b/1")
    @ResponseBody
    public String api2() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "api2";
    }

}
