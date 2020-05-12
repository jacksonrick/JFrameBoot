package com.jf.service;

import com.jf.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description: 分布式事务测试
 * User: xujunfei
 * Date: 2018-02-27
 * Time: 10:19
 */
@Service
public class OrderService {

    @Resource
    private UserService userService;

    /**
     * 分布式事务 - 增加余额
     *
     * @param xid
     * @param money
     */
    public void add(String xid, String money) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        User user = new User(10001);
        user.setMoney(150d);
        //testMapper.update(user);

        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("运行时间： " + (endTime - startTime) + "ms");
    }

    /**
     * 分布式事务 - 减少余额
     *
     * @param xid
     * @param money
     */
    public void reduce(String xid, String money) {
        long startTime = System.currentTimeMillis();   //获取开始时间

        User user = new User(10000);
        user.setMoney(50d);
        //testMapper.update(user);

        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("运行时间： " + (endTime - startTime) + "ms");
    }

}
