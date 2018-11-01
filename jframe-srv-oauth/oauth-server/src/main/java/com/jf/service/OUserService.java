package com.jf.service;

import com.jf.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description: 用户服务类
 * User: xujunfei
 * Date: 2018-10-31
 * Time: 15:35
 */
@Service
public class OUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 模拟账户登录
    private static final String un = "17730215423";

    /**
     * @return
     */
    public User findByUsername(String username) {
        System.out.println("OUserService ----------------- " + username);
        if (!un.equals(username)) {
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setUuid(UUID.randomUUID().toString());
        user.setUserType("1");
        return user;
    }

}
