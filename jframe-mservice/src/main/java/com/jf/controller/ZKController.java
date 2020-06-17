package com.jf.controller;

import com.jf.service.IApiService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description: Zookeeper接口测试
 * User: xujunfei
 * Date: 2019-07-05
 * Time: 11:43
 */
//@RestController
public class ZKController {

    /**
     * RestTemplate支持Ribbon负载均衡
     *
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private IApiService iApiService;

    @GetMapping("/zk/list")
    public String list() {
        return iApiService.list();
    }


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/rest")
    @HystrixCommand(fallbackMethod = "error")
    public String rest() {
        return restTemplate.getForObject("http://ZK-SERVER/list", String.class);
    }

    public String error() {
        return "服务异常，请稍后再试！";
    }
}
