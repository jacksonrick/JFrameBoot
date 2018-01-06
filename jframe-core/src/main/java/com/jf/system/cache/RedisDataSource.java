package com.jf.system.cache;

import redis.clients.jedis.ShardedJedis;

/**
 * Created by xujunfei on 2017/8/1.
 */
public interface RedisDataSource {

    /**
     * 获取redis客户端连接，执行命令
     *
     * @return ShardedJedis
     */
    public abstract ShardedJedis getRedisClient();

    /**
     * 将资源返还个pool
     *
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis);

    /**
     * 出现异常后，将资源返还给pool
     *
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken);

}
