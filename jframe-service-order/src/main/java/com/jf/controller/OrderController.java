package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.service.OrderService;
import com.jf.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @Resource
    private UserService userService;

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

    @GetMapping("/reduce")
    public String reduce(HttpServletRequest request) {
        String xid = request.getHeader("xid");
        System.out.println("xid=" + request.getHeader("xid"));
        String str_money = request.getParameter("money");
        System.out.println("money:" + str_money);

        Double money = Double.parseDouble(str_money);
        Double total_money = userService.findUserById(10000l).getMoney();
        if (total_money < money) {
            return "-1";
        }
        orderService.reduce(xid, str_money);
        return "1";
    }

    @GetMapping("/add")
    public String add(HttpServletRequest request) {
        String xid = request.getHeader("xid");
        System.out.println("xid=" + request.getHeader("xid"));
        String money = request.getParameter("money");
        System.out.println("money:" + money);
        orderService.add(xid, money);
        return "1";
    }

}
