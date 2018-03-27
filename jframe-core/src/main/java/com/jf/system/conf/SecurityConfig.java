package com.jf.system.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring Security
 * User: xujunfei
 * Date: 2018-03-12
 * Time: 18:00
 */
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("spring").password("spring1234").roles("ACTUATOR").build());
        return manager;
    }

    @Configuration
    @Order(1)
    public static class webSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/monitor/**").hasRole("ACTUATOR")
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable() // ajax跨站请求
                    .headers() // header
                    .frameOptions().disable()
            //.contentTypeOptions().disable()
            ;
        }
    }

}
