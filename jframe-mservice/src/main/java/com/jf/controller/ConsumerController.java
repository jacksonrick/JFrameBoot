package com.jf.controller;

import com.jf.model.User;
import com.jf.service.OrderRestService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务消费者
 * User: xujunfei
 * Date: 2020-06-15
 * Time: 15:16
 */
@RestController
@ConditionalOnProperty(value = "spring.application.name", havingValue = "ms-consumer")
public class ConsumerController {

    @Resource
    private OrderRestService orderRestService;

    @GetMapping("/order")
    public String order(Integer userId, Integer productId) {
        return orderRestService.order(userId, productId);
    }

    @GetMapping("/get")
    public String get(@RequestParam Map<String, Object> map) {
        return orderRestService.get(map);
    }

    @PostMapping("/post")
    public String post(@RequestBody User user) {
        return orderRestService.post(user);
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Integer id) {
        return orderRestService.getUserById(id);
    }

}
