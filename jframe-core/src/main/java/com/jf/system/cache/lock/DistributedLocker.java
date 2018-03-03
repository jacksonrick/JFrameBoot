package com.jf.system.cache.lock;

import com.jf.system.conf.UserException;

/**
 * Created with IntelliJ IDEA.
 * Description:获取锁管理类
 * User: xujunfei
 * Date: 2018-01-03
 * Time: 09:53
 */
public interface DistributedLocker {

    /**
     * 获取锁
     *
     * @param resourceName 锁的名称
     * @param worker       获取锁后的处理类
     * @param <T>
     * @return 处理完具体的业务逻辑要返回的数据
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UserException, Exception;

    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UserException, Exception;

}
