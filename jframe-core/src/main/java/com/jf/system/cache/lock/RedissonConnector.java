package com.jf.system.cache.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * Description:获取RedissonClient连接类
 * User: xujunfei
 * Date: 2018-01-03
 * Time: 09:54
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConnector {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
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

}
