package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.service.order.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-02-27
 * Time: 10:19
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @Value("${server.port}")
    private String port;

    /**
     * 订单服务接口
     *
     * @param userId
     * @param productId
     * @return
     */
    @PostMapping("/order")
    public ResMsg order(Long userId, Long productId) {
        System.out.println("订单到达，本机端口：" + port);
        return new ResMsg(0, "下单成功，服务端口：" + port, orderService.order(userId, productId, 100.1, 2));
    }

}
