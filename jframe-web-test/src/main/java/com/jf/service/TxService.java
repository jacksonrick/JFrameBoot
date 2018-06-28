package com.jf.service;

import com.jf.database.mapper.UserMapper;
import com.jf.database.model.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-26
 * Time: 14:30
 */
@Service
public class TxService {

    @Resource
    private UserMapper userMapper;

    @Transactional
    public void tx1() {
        User user1 = new User();
        user1.setNickname("tx1");
        user1.setPassword("1111");
        userMapper.insert(user1);

        // 解决异步方法同步调用的问题 见TaskExecutePool -> @EnableAspectJAutoProxy
        TxService txService = (TxService) AopContext.currentProxy();
        txService.asy();
        System.out.println("over");
    }

    @Transactional
    public void tx2() {
        User user2 = new User();
        user2.setNickname("tx2");
        user2.setPassword("1111");
        userMapper.insert(user2);

        System.out.println(1 / 0);
    }

    @Async
    public void asy() {
        User user3 = new User();
        user3.setNickname("tx3");
        user3.setPassword("1111");
        userMapper.insert(user3);
    }
}
