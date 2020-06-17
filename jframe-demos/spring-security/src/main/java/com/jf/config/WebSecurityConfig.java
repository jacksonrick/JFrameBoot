package com.jf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-11
 * Time: 11:55
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 默认的密码加密方式-BCrypt
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private DataSource dataSource;
    @Resource
    private CustomUserDetailService userDetailService;


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // remember-me使用jdbc存储token
        // 需要创建persistent_logins表
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/dologin").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/bye").permitAll()
                //.and().rememberMe().rememberMeCookieName("RME").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(600).userDetailsService(userDetailService)
                .and().csrf().disable()
        ;
        /**
         1 antMatchers匹配URL，直接过滤
         2 anyRequest任何URL需要授权
         3 formLogin设置登陆页面和校验接口名
         4 logout设置登出接口名和跳转地址
         5 rememberMe记住我，会生成Cookie
         6 禁用跨站请求伪造
         */

        // 过滤器列表：https://docs.spring.io/spring-security/site/docs/5.0.0.M1/reference/htmlsingle/#ns-custom-filters
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication().dataSource(dataSource);
        // 数据库存储用户（需要创建authorities,users表）

        //.inMemoryAuthentication().withUser("admin").password("$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02").roles("ADMIN");
        // 创建一个内存用户
    }

    /**
     * 表DDL
     * create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
     * create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
     * create unique index ix_auth_username on authorities (username,authority);
     * create table persistent_logins (username varchar(64) not null, series varchar(64) primary key,token varchar(64) not null, last_used timestamp not null)
     */

    @Override
    public void configure(WebSecurity web) {
        // 过滤静态资源
        web.ignoring().antMatchers("/static/**");
    }
}
