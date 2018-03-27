package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeWebFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(JframeWebFrontApplication.class, args);
	}

}
