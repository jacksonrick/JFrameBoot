package com.jf.service;


import com.jf.database.mapper.UserMapper;
import com.jf.database.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户Service
 * Created by xujunfei on 2016/12/21.
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 按id查询用户
     *
     * @param id
     * @return
     */
    public User findUserById(Integer id) {
        return userMapper.findById(id);
    }

}
