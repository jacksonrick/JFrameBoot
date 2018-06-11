package com.jf.system.redisson;

import com.jf.string.StringUtil;
import com.jf.system.redisson.lock.AquiredLockWorker;
import com.jf.system.redisson.lock.DistributedLocker;
import com.jf.system.exception.SysException;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
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
    private final static int LOCKER_TIME = 60;
    private final static int WAIT_TIME = 60;

    @Autowired(required = false)
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if (redisProperties.getSentinel() != null) { // Sentinel
            List<String> list = redisProperties.getSentinel().getNodes();
            String[] nodes = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                nodes[i] = "redis://" + list.get(i);
            }
            SentinelServersConfig sentinel = config.useSentinelServers()
                    .addSentinelAddress(nodes)
                    .setTimeout((int) (redisProperties.getTimeout().getSeconds() * 1000));
            if (StringUtil.isNotBlank(redisProperties.getPassword())) {
                sentinel.setPassword(redisProperties.getPassword());
            }
        } else if (redisProperties.getCluster() != null) { // Cluster
            List<String> list = redisProperties.getCluster().getNodes();
            String[] nodes = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                nodes[i] = "redis://" + list.get(i);
            }
            ClusterServersConfig cluster = config.useClusterServers()
                    .addNodeAddress(nodes)
                    .setTimeout((int) (redisProperties.getTimeout().getSeconds() * 1000));
            if (StringUtil.isNotBlank(redisProperties.getPassword())) {
                cluster.setPassword(redisProperties.getPassword());
            }
        } else { // Single
            SingleServerConfig single = config.useSingleServer()
                    .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setTimeout((int) redisProperties.getTimeout().getSeconds() * 1000);
            if (StringUtil.isNotBlank(redisProperties.getPassword())) {
                single.setPassword(redisProperties.getPassword());
            }
        }
        return Redisson.create(config);
    }

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws SysException, Exception {
        return lock(resourceName, worker, LOCKER_TIME);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int leaseTime) throws SysException, Exception {
        // RedissonClient redisson = redissonClient();
        RLock lock = redissonClient.getLock(LOCKER_PREFIX + resourceName);

        // Wait for 60 seconds seconds and automatically unlock it after lockTime seconds
        boolean success = lock.tryLock(WAIT_TIME, leaseTime, TimeUnit.SECONDS);
        if (success) {
            try {
                return worker.invokeAfterLockAquire();
            } finally { // 释放
                lock.unlock();
            }
        }
        throw new SysException("Redisson: tryLock Failed!");
    }

}
