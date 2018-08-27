package com.jf;

import com.jf.commons.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@SpringBootApplication(scanBasePackages = "com.jf")
@EnableResourceServer
public class JframeServiceOrderApplication extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        LogManager.startup("JframeServiceOrderApplication");
        SpringApplication.run(JframeServiceOrderApplication.class, args);
    }

    /**
     * 资源保护
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/monitor/**").authenticated()
                .anyRequest().permitAll()
        ;
    }
}
