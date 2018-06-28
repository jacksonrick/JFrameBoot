package com.jf;

import com.jf.system.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jf")
public class JframeWebManageApplication {

    public static void main(String[] args) {
        LogManager.startup("JframeWebManageApplication");
        SpringApplication.run(JframeWebManageApplication.class, args);
    }

}
