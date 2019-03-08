package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeTestWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JframeTestWsApplication.class, args);
    }
}
