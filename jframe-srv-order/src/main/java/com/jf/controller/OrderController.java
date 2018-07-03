package com.jf.controller;

import com.jf.database.model.User;
import com.jf.entity.ResMsg;
import com.jf.service.OrderService;
import com.jf.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
     * 订单服务接口 Test
     *
     * @param userId
     * @param productId
     * @return
     */
    @GetMapping("/order")
    public ResMsg order(Integer userId, Integer productId) {
        System.out.println("订单到达，本机端口：" + port);
        return new ResMsg(0, "下单成功，服务端口：" + port, orderService.order(userId, productId, 100.1, 2));
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

    // 分布式事务测试

    @GetMapping("/reduce")
    public String reduce(HttpServletRequest request) {
        String xid = request.getHeader("xid");
        System.out.println("xid=" + request.getHeader("xid"));
        String str_money = request.getParameter("money");
        System.out.println("money:" + str_money);

        Double money = Double.parseDouble(str_money);
        Double total_money = userService.findUserById(10000).getMoney();
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
