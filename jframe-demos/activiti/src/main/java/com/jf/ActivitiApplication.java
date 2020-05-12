package com.jf;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-30
 * Time: 11:52
 */
// `exclude`需要排除`org.activiti.spring.boot.SecurityAutoConfiguration`，否则会报错
@SpringBootApplication(scanBasePackages = "com.jf", exclude = SecurityAutoConfiguration.class)
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
