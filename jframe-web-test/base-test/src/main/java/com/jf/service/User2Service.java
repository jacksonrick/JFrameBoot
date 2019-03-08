package com.jf.service;

import com.jf.database.mapper.TestMapper;
import com.jf.database.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-05-15
 * Time: 10:49
 */
@Service
public class User2Service {

    @Resource
    private TestMapper testMapper;

    @Transactional
    public void update(User user) throws Exception {
        testMapper.update(user);
    }

}
