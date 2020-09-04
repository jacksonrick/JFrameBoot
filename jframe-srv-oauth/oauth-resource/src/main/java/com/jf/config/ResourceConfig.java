package com.jf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * Description: 资源服务配置
 * User: xujunfei
 * Date: 2020-08-14
 * Time: 17:15
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/monitor/**").authenticated() // 只拦截/monitor/**
                .anyRequest().permitAll()
        ;
    }

}
