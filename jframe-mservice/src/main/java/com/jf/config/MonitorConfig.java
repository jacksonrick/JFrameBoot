package com.jf.config;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 如果使用Eureka注册中心，启用该类并正确配置oauth，可监控客户端
 */
//@Configuration
//@EnableResourceServer
//public class MonitorConfig extends ResourceServerConfigurerAdapter {

    // 对/monitor/**进行资源保护
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests().antMatchers("/monitor/**").authenticated()
//                .anyRequest().permitAll()
//        ;
//    }
//}
