package com.jf;

import com.jf.commons.LogManager;
import com.jf.system.conf.annotation.EnableCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
@EnableCache
public class JframeWebAppApplication {

	public static void main(String[] args) {
		LogManager.startup("JframeWebAppApplication");
		SpringApplication.run(JframeWebAppApplication.class, args);
	}
}
