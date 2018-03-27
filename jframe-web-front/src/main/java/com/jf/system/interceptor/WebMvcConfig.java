package com.jf.system.interceptor;

import com.jf.system.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * Description: 拦截器
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 10:29
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UserInterceptor interceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor()).excludePathPatterns(Constant.excludePathPatterns);
        super.addInterceptors(registry);
    }

}
