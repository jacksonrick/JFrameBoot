package com.jf;

import com.jf.commons.LogManager;
import com.jf.system.conf.annotation.EnableCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
@EnableCache
public class JframeWebApiApplication {

	public static void main(String[] args) {
		LogManager.startup("JframeWebApiApplication");
		SpringApplication.run(JframeWebApiApplication.class, args);
	}
}
