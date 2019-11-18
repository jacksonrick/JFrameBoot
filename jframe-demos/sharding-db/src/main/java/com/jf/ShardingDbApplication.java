package com.jf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-11-14
 * Time: 10:12
 */
@SpringBootApplication(scanBasePackages = "com.jf")
public class ShardingDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingDbApplication.class, args);
    }
}
