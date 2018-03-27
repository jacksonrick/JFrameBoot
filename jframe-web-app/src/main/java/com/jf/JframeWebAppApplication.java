package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JframeWebAppApplication.class, args);
	}
}
