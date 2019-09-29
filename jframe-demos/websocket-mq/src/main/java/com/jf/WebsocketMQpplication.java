package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class WebsocketMQpplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketMQpplication.class, args);
    }
}
