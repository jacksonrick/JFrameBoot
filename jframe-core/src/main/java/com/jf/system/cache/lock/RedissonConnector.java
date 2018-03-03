package com.jf.system.cache.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * Description: 获取RedissonClient连接类
 * User: xujunfei
 * Date: 2018-01-03
 * Time: 09:54
 */
@Configuration
@ConditionalOnProperty(name = "app.cache.redisson.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConnector {

    private String host;
    private int port;
    private int timeout;

    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress(host + ":" + port).setTimeout(timeout);
        redisson = Redisson.create(config);
    }

    public RedissonClient getClient() {
        return redisson;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
