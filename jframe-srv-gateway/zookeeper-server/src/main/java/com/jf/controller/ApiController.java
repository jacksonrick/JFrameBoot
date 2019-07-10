package com.jf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-05
 * Time: 10:42
 */
@RestController
@RefreshScope
public class ApiController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/list")
    public String list() {
        return "Server response success at port: " + port;
    }

    // spring环境配置管理
    @Autowired
    private Environment env;

    @GetMapping("/env")
    public String env(String prop) {
        return this.env.getProperty(prop, "Not Found");
    }

    // 指定键
    @Value("${name}")
    private String name;

    @GetMapping("/env2")
    public String env2() {
        return this.name;
    }

}
