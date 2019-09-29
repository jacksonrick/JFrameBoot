package com.jf.srvs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-05-07
 * Time: 11:12
 */
@Component
public class ZkConfig {

    @Value("${zookeeper.address}")
    private String address;

    @Bean
    public ServiceRegistry serviceRegistry() {
        return new ServiceRegistryImpl(address);
    }

}
