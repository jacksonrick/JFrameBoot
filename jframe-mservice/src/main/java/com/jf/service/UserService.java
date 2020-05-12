package com.jf.service;

import com.jf.model.User;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-05-14
 * Time: 13:35
 */
@Service
public class UserService {

    /**
     * @param id
     * @return
     */
    public User findUserById(Integer id) {
        User user = new User(1001);
        user.setNickname("xxx");
        user.setMoney(99.99d);
        return user;
    }

}
