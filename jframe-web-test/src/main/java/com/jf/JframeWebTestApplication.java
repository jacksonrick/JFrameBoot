package com.jf;

import com.jf.system.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
//@EnableFeignClients
//@EnableHystrix
public class JframeWebTestApplication {

	public static void main(String[] args) {
		LogManager.startup("JframeWebTestApplication");
		SpringApplication.run(JframeWebTestApplication.class, args);
	}
}
