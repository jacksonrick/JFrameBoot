package com.jf.service;

import com.jf.mapper.UserMapper;
import com.jf.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-11-14
 * Time: 10:21
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User findUserById(Integer id) {
        return userMapper.findById(id);
    }

    public void insertUser(Integer id, String username, Integer age) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setAge(age);
        userMapper.insert(user);
    }

    @Transactional
    public void testTransaction() {
        User user = userMapper.findById(1);
        user.setUsername("aaa");
        userMapper.update(user);

        User user1 = new User();
        user1.setId(33);
        user1.setUsername("zzz");
        user1.setAge(10);
        userMapper.insert(user1);

        System.out.println(1/0);
    }

}
