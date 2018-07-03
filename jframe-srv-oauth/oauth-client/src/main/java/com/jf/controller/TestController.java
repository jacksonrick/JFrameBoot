package com.jf.controller;

import com.jf.config.OAuthClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-05
 * Time: 15:16
 */
@RestController
public class TestController {

    @Resource
    private OAuthClient oAuthClient;

    @GetMapping("/test1")
    public Object test1() {
        return oAuthClient.get("http://127.0.0.1:8010");
    }

    @GetMapping("/test2")
    public Object test2() {
        return oAuthClient.getAuth("http://127.0.0.1:8010/monitor/a");
    }

}
