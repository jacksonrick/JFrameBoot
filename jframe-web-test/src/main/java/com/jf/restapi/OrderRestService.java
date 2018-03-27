package com.jf.restapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping("/order")
    String order(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId);

}
