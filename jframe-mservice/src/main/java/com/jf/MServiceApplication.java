package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.jf")
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class MServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MServiceApplication.class, args);
    }

}
