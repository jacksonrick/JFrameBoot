package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class JframeSrvGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(JframeSrvGatewayApplication.class, args);
    }
}
