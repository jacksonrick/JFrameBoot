package com.jf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-05
 * Time: 15:16
 */
@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test1")
    public Object test1() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://127.0.0.1:8010", null);
        System.out.println(response);
        return response.getBody();
    }

    @GetMapping("/test2")
    public Object test2() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://127.0.0.1:8010/monitor/a", null);
        System.out.println(response);
        return response.getBody();
    }


    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    /**
     * 测试SSO Client
     * 请在application.yml配置正确的参数
     *
     * @return
     */
    @GetMapping("/test3")
    public String test3() {
        try {
            ResponseEntity<String> response = oAuth2RestTemplate.exchange("http://127.0.0.1:8010/monitor/a", HttpMethod.GET, null, String.class);
            System.out.println(response);
            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "401";
        }
    }

}
