package com.jf.config;

import com.jf.service.OUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring Security配置
 * User: xujunfei
 * Date: 2018-06-07
 * Time: 09:59
 */
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OUserDetailService oUserDetailService;
    @Autowired
    private OAuthAuthenticationProvider oAuthAuthenticationProvider;

    @Autowired
    @Qualifier("oauthAuthenticationDetailsSource")
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    /**
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义的验证方式
        auth.authenticationProvider(oAuthAuthenticationProvider);
        // Dao方式
        //auth.userDetailsService(oUserDetailService).passwordEncoder(passwordEncoder());
    }

    /**
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/authentication/require").loginProcessingUrl("/authentication/form")
                .authenticationDetailsSource(authenticationDetailsSource)
                .and()
                .antMatcher("/**").authorizeRequests().antMatchers("/oauth/**").permitAll()
                .and()
                .csrf().disable()
        ;
    }

}
