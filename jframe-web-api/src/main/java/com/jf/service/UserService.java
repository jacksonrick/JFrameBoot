package com.jf.service;


import com.jf.common.TokenHandler;
import com.jf.database.mapper.TokenMapper;
import com.jf.database.mapper.UserMapper;
import com.jf.database.model.Token;
import com.jf.database.model.User;
import com.jf.date.DateUtil;
import com.jf.entity.ResMsg;
import com.jf.string.IdGen;
import com.jf.string.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户Service
 * Created by xujunfei on 2016/12/21.
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private TokenMapper tokenMapper;

    @Resource
    private TokenHandler tokenHandler;

    /**
     * 按id查询用户
     *
     * @param id
     * @return
     */
    public User findUserById(Long id) {
        return userMapper.findById(id);
    }

    /**
     * @param phone
     * @return
     */
    public int findCountByPhone(String phone) {
        return userMapper.findCountByKey("phone", phone);
    }

    /**
     * @param phone
     * @return
     */
    public Long findIdByPhone(String phone) {
        return userMapper.findIdByPhone(phone);
    }

    /**
     * @param loginId
     * @param password
     * @return
     */
    public int findCountByIdAndPwd(Long userId, String password) {
        return userMapper.findCountByIdAndPwd(userId, password);
    }

    /**
     * @param user
     * @return
     */
    public int updateUserInfo(User user) {
        return userMapper.update(user);
    }

    /**
     * @param user
     * @return
     */
    @Transactional
    public String register(User user) {
        user.setId(IdGen.get().nextId()); // id
        int res = userMapper.insert(user);
        if (res > 0) {
            return tokenHandler.bindTokenToDb(user.getId());
        }
        return "";
    }

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @return
     */
    @Transactional
    public ResMsg login(String phone, String password) {
        User user = userMapper.findByPhone(phone);
        if (user == null) {
            return ResMsg.fail("账号不存在");
        }
        if (user.getDeleted()) {
            return ResMsg.fail("账号已被封停");
        }
        if (user.getLocked()) {
            return ResMsg.fail("账号已被锁定，请次日重试");
        }
        if (!password.equals(user.getPassword())) {
            if (user.getWrongPwd() >= 3) {
                User u = User.Builder.anUser().id(user.getId()).locked(true).build();
                userMapper.update(u);
                return ResMsg.fail("密码输入次数过多，账号已被锁定");
            }
            // 密码错误次数加1
            User u = User.Builder.anUser().id(user.getId()).wrongPwd(user.getWrongPwd() + 1).build();
            userMapper.update(u);
            return ResMsg.fail("登陆密码错误");
        }

        user.setPassword(null);
        user.setToken(tokenHandler.bindToken(user.getId()));
        return ResMsg.success("登陆成功", user);
    }

    /**
     * @param userId
     */
    public int logout(Long userId) {
        return tokenMapper.delete(String.valueOf(userId));
    }

}
