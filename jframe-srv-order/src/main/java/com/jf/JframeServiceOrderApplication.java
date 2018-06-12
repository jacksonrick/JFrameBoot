package com.jf;

import com.jf.system.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeServiceOrderApplication {

    public static void main(String[] args) {
        LogManager.startup("JframeServiceOrderApplication");
        SpringApplication.run(JframeServiceOrderApplication.class, args);
    }
}
