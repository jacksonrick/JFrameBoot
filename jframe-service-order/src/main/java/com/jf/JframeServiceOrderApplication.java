package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeServiceOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JframeServiceOrderApplication.class, args);
    }
}
