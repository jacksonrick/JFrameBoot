package com.jf;

import com.jf.config.handler.Md5PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OAuthServerApplication {

    /**
     * 密码加密方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

        // 可选 Md5PasswordEncoder()
    }


    public static void main(String[] args) {
        SpringApplication.run(OAuthServerApplication.class, args);
    }
}
