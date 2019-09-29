package com.jf.health;

import com.jf.util.ProcessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义健康检查
 * User: xujunfei
 * Date: 2019-08-13
 * Time: 10:22
 */
@Component
public class MyHealthIndicator implements HealthIndicator {

    private static Logger log = LoggerFactory.getLogger(MyHealthIndicator.class);

    static final String REDIS_PING = "redis-cli -h 127.0.0.1 -p 6379 ping";
    static final String REDIS_CLI = "redis-cli -v";

    @Override
    public Health health() {
        String result = ProcessUtil.process(REDIS_PING);
        if (!"PONG".equals(result)) {
            return Health.down().build();
        } else {
            String version = ProcessUtil.process(REDIS_CLI);
            return Health.up().withDetail("VERSION", version).build();
        }
    }
}
