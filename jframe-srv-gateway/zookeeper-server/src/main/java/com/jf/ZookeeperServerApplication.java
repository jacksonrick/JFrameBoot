package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-05
 * Time: 10:38
 */
@SpringBootApplication(scanBasePackages = "com.jf")
@EnableDiscoveryClient
public class ZookeeperServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperServerApplication.class, args);
    }

}
