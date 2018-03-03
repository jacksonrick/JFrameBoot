package com.jf.system.cache.lock;

import com.jf.system.conf.UserException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 分布式事务-redisson
 * User: xujunfei
 * Date: 2018-01-03
 * Time: 09:55
 */
@Configuration
@ConditionalOnProperty(name = "app.cache.redisson.enabled", havingValue = "true")
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";
    private final static int LOCKER_TIME = 100;
    private final static int WAIT_TIME = 100;

    @Resource
    RedissonConnector redissonConnector;

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UserException, Exception {
        return lock(resourceName, worker, LOCKER_TIME);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int leaseTime) throws UserException, Exception {
        RedissonClient redisson = redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);

        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
        boolean success = lock.tryLock(WAIT_TIME, leaseTime, TimeUnit.SECONDS);
        if (success) {
            try {
                return worker.invokeAfterLockAquire();
            } finally { // 释放
                lock.unlock();
            }
        }
        throw new UserException();
    }
}
