package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@ComponentScan(basePackages = {"com.jf"})
public class JframeWebFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(JframeWebFrontApplication.class, args);
	}
}
