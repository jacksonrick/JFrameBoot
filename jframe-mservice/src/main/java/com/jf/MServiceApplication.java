package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 微服务模块 提供Eureka和Zookeeper注册中心方式的案例
 * 该案例仅用于参考，不能用于生产环境
 */
@SpringBootApplication(scanBasePackages = "com.jf")
@EnableDiscoveryClient
@EnableFeignClients
public class MServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MServiceApplication.class, args);
    }

}
