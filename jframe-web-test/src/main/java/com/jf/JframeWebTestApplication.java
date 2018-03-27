package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.jf")
//@EnableFeignClients
//@EnableHystrix
public class JframeWebTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JframeWebTestApplication.class, args);
	}
}
