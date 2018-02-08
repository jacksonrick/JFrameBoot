package com.jf.cloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-02-06
 * Time: 15:48
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/logout", "/static/**").permitAll();
        http.authorizeRequests().antMatchers("/**").authenticated();

        http.formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error").permitAll();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
        http.csrf().disable();
        http.httpBasic();
    }

}
