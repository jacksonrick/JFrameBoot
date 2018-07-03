package com.jf.restapi;

import com.jf.database.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: Feign方式实现，自带负载均衡
 * User: xujunfei
 * Date: 2018-02-27
 * Time: 10:27
 */
@FeignClient(value = "JFRAME-SERVICE-ORDER", fallback = OrderRestServiceHystrix.class)
public interface OrderRestService {

    /**
     * 下单服务
     *
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/order")
    String order(@RequestParam("userId") Integer userId, @RequestParam("productId") Integer productId);

    /**
     * GET请求多个参数
     *
     * @param map
     * @return
     */
    @GetMapping("/get")
    String get(@RequestParam Map<String, Object> map);

    /**
     * POST请求多个参数
     *
     * @param user
     * @return
     */
    @PostMapping("/post")
    String post(@RequestBody User user);

}
