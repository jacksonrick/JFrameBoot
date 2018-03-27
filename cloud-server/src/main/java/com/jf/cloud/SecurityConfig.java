package com.jf.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-02-06
 * Time: 15:48
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${eureka.security.username}")
    private String username;

    @Value("${eureka.security.password}")
    private String password;

    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username(username).password(password).roles("ADMIN").build());
        return manager;
    }

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
