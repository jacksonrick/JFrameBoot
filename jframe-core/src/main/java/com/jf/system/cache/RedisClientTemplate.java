package com.jf.system.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisClientTemplate {

    public abstract String set(String key, String value);

    public abstract String get(String key);

    public abstract Boolean exists(String key);

    public abstract String type(String key);

    public abstract Long expire(String key, int seconds);

    public abstract Long expireAt(String key, long unixTime);

    public abstract Long ttl(String key);

    public abstract boolean setbit(String key, long offset, boolean value);

    public abstract boolean getbit(String key, long offset);

    public abstract long setrange(String key, long offset, String value);

    public abstract String getrange(String key, long startOffset, long endOffset);

    public abstract String getSet(String key, String value);

    public abstract Long setnx(String key, String value);

    public abstract String setex(String key, int seconds, String value);

    public abstract Long decrBy(String key, long integer);

    public abstract Long decr(String key);

    public abstract Long incrBy(String key, long integer);

    public abstract Long incr(String key);

    public abstract Long append(String key, String value);

    public abstract String substr(String key, int start, int end);

    public abstract Long hset(String key, String field, String value);

    public abstract String hget(String key, String field);

    public abstract Long hsetnx(String key, String field, String value);

    public abstract String hmset(String key, Map<String, String> hash);

    public abstract List<String> hmget(String key, String... fields);

    public abstract Long hincrBy(String key, String field, long value);

    public abstract Boolean hexists(String key, String field);

    public abstract Long del(String key);

    public abstract Long hdel(String key, String field);

    public abstract Long hlen(String key);

    public abstract Set<String> hkeys(String key);

    public abstract List<String> hvals(String key);

    public abstract Map<String, String> hgetAll(String key);

}
