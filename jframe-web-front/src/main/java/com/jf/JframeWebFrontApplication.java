package com.jf;

import com.jf.commons.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeWebFrontApplication {

	public static void main(String[] args) {
		LogManager.startup("JframeWebFrontApplication");
		SpringApplication.run(JframeWebFrontApplication.class, args);
	}

}
