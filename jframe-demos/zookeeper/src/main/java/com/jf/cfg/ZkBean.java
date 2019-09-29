package com.jf.cfg;

import com.jf.cfg.ZookeeperConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-05-08
 * Time: 11:59
 */
@Configuration
public class ZkBean {

    // bin/zkCli.sh
    // create /cfgs ''
    // create /cfgs/name jack
    // create /cfgs/age 20

    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.config-path}")
    private String configPath;

    @Bean
    public ZookeeperConfigurer zookeeperConfigurer() {
        return new ZookeeperConfigurer(address, configPath);
    }

}
