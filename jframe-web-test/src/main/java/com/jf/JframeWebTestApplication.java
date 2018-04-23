package com.jf;

import com.jf.system.conf.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.jf")
//@EnableFeignClients
//@EnableHystrix
public class JframeWebTestApplication {

	public static void main(String[] args) {
		LogManager.startup("JframeWebTestApplication");
		SpringApplication.run(JframeWebTestApplication.class, args);
	}
}
