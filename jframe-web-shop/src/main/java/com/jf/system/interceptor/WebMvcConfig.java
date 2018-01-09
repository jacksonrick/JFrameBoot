package com.jf.system.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * Description: 商家管理系统拦截器 /shop/**
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 10:29
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ShopInterceptor myInterceptor() {
        return new ShopInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor()).addPathPatterns("/shop/**");
        super.addInterceptors(registry);
    }

}
