package com.jf.system.cache;

import com.jf.system.cache.lock.AquiredLockWorker;
import com.jf.system.cache.lock.DistributedLocker;
import com.jf.system.exception.SysException;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 分布式事务-redisson
 * User: xujunfei
 * Date: 2018-01-03
 * Time: 09:55
 */
@Configuration
@ConditionalOnProperty(name = "app.redisson.enabled", havingValue = "true")
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";
    private final static int LOCKER_TIME = 100;
    private final static int WAIT_TIME = 100;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedissonProperties redissonProperties() {
        RedissonProperties properties = new RedissonProperties();
        return properties;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redissonProperties().getHost() + ":" + redissonProperties().getPort())
                .setTimeout((int) redissonProperties().getTimeout().getSeconds());
        return Redisson.create(config);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws SysException, Exception {
        return lock(resourceName, worker, LOCKER_TIME);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int leaseTime) throws SysException, Exception {
        RedissonClient redisson = redissonClient();
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
        throw new SysException();
    }

    class RedissonProperties {

        private String host;
        private int port;
        private Duration timeout;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public Duration getTimeout() {
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            this.timeout = timeout;
        }
    }
}
