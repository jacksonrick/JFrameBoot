package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created with IntelliJ IDEA.
 * Description: zookeeper注册中心-客户端
 * User: xujunfei
 * Date: 2019-07-05
 * Time: 10:38
 */
@SpringBootApplication(scanBasePackages = "com.jf")
@EnableDiscoveryClient
@EnableFeignClients
public class SrvDemoZkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvDemoZkApplication.class, args);
    }

}
