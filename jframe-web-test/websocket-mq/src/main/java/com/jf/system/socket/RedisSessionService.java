package com.jf.system.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: Redis服务类
 * User: xujunfei
 * Date: 2019-03-08
 * Time: 15:22
 */
@Component
public class RedisSessionService {

    @Autowired
    private RedisTemplate<String, String> template;

    private final static String PREFIX = "spr:ses:socket:uid:";

    /**
     * 在缓存中保存用户和websocket sessionid的信息
     *
     * @param name
     * @param wsSessionId
     */
    public void add(String name, String wsSessionId) {
        BoundValueOperations<String, String> boundValueOperations = template.boundValueOps(PREFIX + name);
        boundValueOperations.set(wsSessionId, 24 * 3600, TimeUnit.SECONDS);
    }

    /**
     * 从缓存中删除用户的信息
     *
     * @param name
     */
    public boolean del(String name) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] rawKey = template.getStringSerializer().serialize(PREFIX + name);
                return connection.del(rawKey) > 0;
            }
        }, true);
    }

    /**
     * 根据用户id获取用户对应的sessionId值
     *
     * @param name
     * @return
     */
    public String get(String name) {
        BoundValueOperations<String, String> boundValueOperations = template.boundValueOps(PREFIX + name);
        return boundValueOperations.get();
    }
}
