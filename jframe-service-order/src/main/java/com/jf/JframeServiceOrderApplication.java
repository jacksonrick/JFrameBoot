package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.jf"})
public class JframeServiceOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JframeServiceOrderApplication.class, args);
	}
}
