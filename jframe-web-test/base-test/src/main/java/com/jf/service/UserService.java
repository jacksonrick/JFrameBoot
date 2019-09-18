package com.jf.service;

import com.github.pagehelper.PageInfo;
import com.jf.database.mapper.TestMapper;
import com.jf.database.model.User;
import com.jf.encrypt.PasswordUtil;
import com.jf.string.StringUtil;
import com.jf.system.redisson.RedisLocker;
import com.jf.system.redisson.lock.AquiredLockWorker;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TestMapper testMapper;

    // RedisLocker
    @Autowired(required = false)
    private RedisLocker locker;

    /**
     * 测试Redisson分布式锁
     *
     * @param userId
     */
    public void testLock(Integer userId) throws Exception {
        locker.lock("user_" + userId + "_lock", new AquiredLockWorker<Object>() {
            @Override
            public Object invokeAfterLockAquire() throws Exception {

                User user = testMapper.findSimpleById(userId);
                user.setMoney(user.getMoney() - 1);
                testMapper.update(user);

                return null;
            }
        });
    }

    /**
     * 按id查询用户
     * <p>测试Redis缓存</p>
     *
     * @param id
     * @return
     */
    @Cacheable(value = "user", key = "'findUserById'+#id")
    public User findUserById(Integer id) {
        return testMapper.findById(id);
    }


    /**
     * 用户更新信息
     * <p>测试Redis缓存-更新会直接删除老数据</p>
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "user", key = "'findUserById'+#user.id")
    public int updateUser(User user) {
        if (StringUtil.isNotBlank(user.getPassword())) {
            user.setPassword(PasswordUtil.MD5Encode(user.getPassword()));
        }
        return testMapper.update(user);
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
        List<User> list = testMapper.findByCondition(condition);
        return new PageInfo(list);
    }

    /**
     * @param condition
     * @return
     */
    public List<User> findByCondition(User condition) {
        //condition.setPageNo(1);
        //condition.setPageSize(10);
        //condition.setPage(false);
        return testMapper.findByCondition(condition);
    }

}
