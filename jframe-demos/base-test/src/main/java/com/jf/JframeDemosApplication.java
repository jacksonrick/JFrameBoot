package com.jf;

import com.jf.commons.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication(scanBasePackages = "com.jf")
//@EnableOAuth2Sso
@EnableAsync
public class JframeDemosApplication {

	public static void main(String[] args) {
		LogManager.startup("JframeDemosApplication");
		SpringApplication.run(JframeDemosApplication.class, args);
	}
}
