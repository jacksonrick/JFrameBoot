package com.jf;

import com.jf.commons.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.jf")
@EnableAsync
//@EnableJMX
//@EnableCache
public class JframeWebManageApplication {

    public static void main(String[] args) {
        LogManager.startup("JframeWebManageApplication");
        SpringApplication.run(JframeWebManageApplication.class, args);
    }

}
