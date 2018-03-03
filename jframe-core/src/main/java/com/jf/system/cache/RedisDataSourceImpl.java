package com.jf.system.cache;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * Created by xujunfei on 2017/8/1.
 */
//@Configuration("redisDataSource")
//@AutoConfigureAfter(JedisConfig.class)
@Deprecated
public class RedisDataSourceImpl implements RedisDataSource {

    @Resource
    private ShardedJedisPool shardedJedisPool;

    /**
     * 获取redis客户端连接，执行命令
     *
     * @return ShardedJedis
     */
    public ShardedJedis getRedisClient() {
        try {
            ShardedJedis shardedJedis = shardedJedisPool.getResource();
            return shardedJedis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将资源返还给pool
     *
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }

    /**
     * 出现异常后，将资源返还给pool
     *
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }

    }
}
