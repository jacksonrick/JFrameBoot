package com.jf.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring Security
 * User: xujunfei
 * Date: 2018-03-12
 * Time: 18:00
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("springboot123456!");
        System.out.println(encode);
    }*/

    @Autowired
    private SecurityProperties properties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<String> list = properties.getUser().getRoles();
        String[] roles = new String[list.size()];
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser(properties.getUser().getName())
                .password(properties.getUser().getPassword())
                .roles(list.toArray(roles));
    }

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
