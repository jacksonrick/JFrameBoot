package com.jf.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: Redis分布式锁 v1.0
 * <p>单机版，分布式有待研究；还可以使用Redisson</p>
 * User: xujunfei
 * Date: 2020-07-23
 * Time: 10:28
 */
@Component
public class RedisLocker {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取锁
     *
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        return stringRedisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @param requestId
     * @return
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptText("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end");
        Long result = stringRedisTemplate.execute(script, Collections.singletonList(lockKey), requestId);
        return result == 1L;
    }

}
