package com.jf.service.user;

import com.github.pagehelper.PageInfo;
import com.jf.mapper.UserMapper;
import com.jf.model.User;
import com.jf.model.custom.IdText;
import com.jf.string.StringUtil;
import com.jf.system.cache.lock.AquiredLockWorker;
import com.jf.system.cache.lock.RedisLocker;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户Service
 * Created by xujunfei on 2016/12/21.
 */
@Service
@Transactional
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 测试Redisson分布式锁
     *
     * @param userId
     */
    /*public void testLock(Long userId) {
        try {
            locker.lock("user_" + userId + "_lock", new AquiredLockWorker<Object>() {
                @Override
                public Object invokeAfterLockAquire() throws Exception {

                    User user = userMapper.findSimpleById(userId);
                    user.setMoney(user.getMoney() - 1);
                    userMapper.update(user);

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 按id查询用户
     * <p>测试Redis缓存</p>
     *
     * @param id
     * @return
     */
    @Cacheable(value = "common", key = "'findUserById'+#id")
    public User findUserById(Long id) {
        return userMapper.findById(id);
    }


    /**
     * 用户更新信息
     * <p>测试Redis缓存</p>
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "common", key = "'findUserById'+#user.id")
    public int updateUser(User user) {
        if (StringUtil.isNotBlank(user.getPassword())) {
            user.setPassword(StringUtil.MD5Encode(user.getPassword()));
        }
        return userMapper.update(user);
    }

    /**
     * 用户分页查询
     *
     * @param condition
     * @return
     */
    public PageInfo findUserByPage(User condition) {
        // 需要自定义排序的，必须指定默认排序
        if (StringUtil.isBlank(condition.getPageSort())) {
            condition.setPageSort("u.id DESC");
        }
        condition.setPage(true);
        List<User> list = userMapper.findByCondition(condition);
        return new PageInfo(list);
    }

    /**
     * @param condition
     * @return
     */
    public List<User> findByCondition(User condition) {
        return userMapper.findByCondition(condition);
    }

    /**
     * 通过id查询字段
     * <p>只能在service或controller调用，禁止直接在request传参</p>
     *
     * @param userId
     * @param field
     * @return
     */
    public Object findFieleByUserId(Long userId, String field) {
        return userMapper.findFieleByUserId(userId, field);
    }

    /**
     * 手机号模糊查询
     *
     * @param phone
     * @return
     */
    public List<IdText> findUserLikePhone(String phone) {
        return userMapper.findUserLikePhone(phone);
    }

    /**
     * 按手机号查询
     *
     * @param phone
     * @return
     */
    public User findUserByPhone(String phone) {
        User condition = new User();
        condition.setPhone(phone);
        List<User> list = userMapper.findByCondition(condition);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 按手机号查询总数
     *
     * @param phone
     * @return
     */
    public int findUserCountByPhone(String phone) {
        return userMapper.findCountByKey("phone", phone);
    }

    /**
     * 按邮箱查询总数
     *
     * @param email
     * @return
     */
    public int findUserCountByEmail(String email) {
        return userMapper.findCountByKey("email", email);
    }

    /**
     * 账号密码查询
     *
     * @param account
     * @param password
     * @return
     */
    public User findUserByNameAndPwd(String account, String password) {
        return userMapper.findByNameAndPwd(account, StringUtil.MD5Encode(password));
    }

    /**
     * 新增用户
     *
     * @param nickname
     * @param email
     * @param password
     * @param phone
     * @return
     */
    public int insertUser(String nickname, String email, String password, String phone) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(StringUtil.MD5Encode(password));
        user.setPhone(phone);
        user.setNickname(nickname);
        // 新增用户
        return userMapper.insert(user);
    }

    /**
     * 更新用户头像
     *
     * @param userId
     * @param avatar
     * @return
     */
    public int updateUserAvatar(Long userId, String avatar) {
        User user = new User();
        user.setId(userId);
        user.setAvatar(avatar);
        return userMapper.update(user);
    }

    /**
     * 删除用户(逻辑删除)
     *
     * @param userId
     * @return
     */
    public int deleteUser(Long userId) {
        return userMapper.delete(userId);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public int deleteBatch(Long[] ids) {
        return userMapper.deleteBatch(ids);
    }

}
