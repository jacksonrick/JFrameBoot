package com.jf.controller;

import com.jf.model.User;
import com.jf.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务提供者API
 * User: xujunfei
 * Date: 2018-02-27
 * Time: 10:19
 */
@RestController
@ConditionalOnProperty(value = "spring.application.name", havingValue = "ms-provider")
public class ProviderController {

    @Resource
    private UserService userService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/order")
    public String order(Integer userId, Integer productId) {
        System.out.println("订单到达，本机端口：" + port + "，userId=" + userId + "，productId=" + productId);
        return "下单成功，服务端口：" + port;
    }

    @GetMapping("/get")
    public String get(@RequestParam Map<String, Object> map) {
        System.out.println(map);
        return "get";
    }

    @PostMapping("/post")
    public String post(@RequestBody User user) {
        System.out.println(user);
        return "post";
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Integer id) {
        System.out.println(id);
        return userService.findUserById(id);
    }

}
