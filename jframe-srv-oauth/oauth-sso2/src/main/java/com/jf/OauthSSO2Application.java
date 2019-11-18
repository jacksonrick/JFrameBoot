package com.jf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@Controller
@EnableOAuth2Sso
public class OauthSSO2Application extends WebSecurityConfigurerAdapter {

    @Value("${auth-server}")
    private String authServer;

    /**
     * 退出登录
     *
     * @param session
     */
    @RequestMapping("/sso/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:" + authServer + "/logout";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Authentication user(Authentication user) {
        System.out.println("user: " + user.getName());
        return user;
    }

    /**
     * 配置不拦截请求
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/sso/logout");
    }

    public static void main(String[] args) {
        SpringApplication.run(OauthSSO2Application.class, args);
    }

}
