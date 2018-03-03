package com.jf.system.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Redis jedis配置
 * User: xujunfei
 * Date: 2017-12-12
 * Time: 14:28
 */
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis")
@Deprecated
public class JedisConfig {

    // 主机
    private String host;
    // 端口
    private int port;
    // 密码（如果需要）
    private String password;
    // 超时
    private int timeout;
    // 最大空闲
    private int maxIdle;
    // 初始化连接数
    private int minIdle;
    // 最大等待时间
    private int maxWaitMillis;
    // 最大分配的对象数
    private int maxTotal;
    // 对拿到的connection进行validateObject校验
    private boolean testOnBorrow;
    // 在进行returnObject对返回的connection进行validateObject校验
    private boolean testOnReturn;

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        return config;
    }

    @Bean
    public ShardedJedisPool getShardedJedisPool() {
        JedisPoolConfig config = getRedisConfig();
        List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
        JedisShardInfo jedis1 = new JedisShardInfo(host, port, timeout);
        list.add(jedis1);
        ShardedJedisPool pool = new ShardedJedisPool(config, list);
        return pool;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    @Override
    public String toString() {
        return "JedisConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                ", maxIdle=" + maxIdle +
                ", minIdle=" + minIdle +
                ", maxWaitMillis=" + maxWaitMillis +
                ", maxTotal=" + maxTotal +
                ", testOnBorrow=" + testOnBorrow +
                ", testOnReturn=" + testOnReturn +
                '}';
    }
}
