package com.jf;

import com.jf.commons.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeWebAppApplication {

	public static void main(String[] args) {
		LogManager.startup("JframeWebAppApplication");
		SpringApplication.run(JframeWebAppApplication.class, args);
	}
}
