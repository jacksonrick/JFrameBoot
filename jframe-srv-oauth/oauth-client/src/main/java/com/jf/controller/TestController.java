package com.jf.controller;

import com.jf.config.OAuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/test")
    public Object test() {
        return oAuthClient.get("http://127.0.0.1:8010/");
    }

}
