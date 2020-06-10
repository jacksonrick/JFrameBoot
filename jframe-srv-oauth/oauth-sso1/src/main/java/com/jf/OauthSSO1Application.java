package com.jf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableOAuth2Sso
public class OauthSSO1Application extends WebSecurityConfigurerAdapter {

    @Value("${auth-server}")
    private String authServer;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/sso/logout").logoutSuccessUrl(authServer + "/logout")
                .and()
                .csrf().disable();
    }

    public static void main(String[] args) {
        SpringApplication.run(OauthSSO1Application.class, args);
    }

}
