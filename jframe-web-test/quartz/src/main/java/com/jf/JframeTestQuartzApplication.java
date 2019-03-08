package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeTestQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(JframeTestQuartzApplication.class, args);
    }
}
