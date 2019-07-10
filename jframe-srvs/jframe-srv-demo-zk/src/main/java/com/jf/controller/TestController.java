package com.jf.controller;

import com.jf.service.IApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-05
 * Time: 11:43
 */
@RestController
public class TestController {

    @Autowired
    private IApiService iApiService;

    @GetMapping("/test")
    public String test() {
        return iApiService.list();
    }

    // restTemplate
    @Bean
    @LoadBalanced
    RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate rest;

    @RequestMapping("/rest")
    public String rest() {
        return this.rest.getForObject("http://ZK-SERVER/list", String.class);
    }

}
