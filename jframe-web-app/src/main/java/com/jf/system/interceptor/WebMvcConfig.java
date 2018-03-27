package com.jf.system.interceptor;

import com.jf.system.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: APP拦截器、参数拦截
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 10:29
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AppInterceptor interceptor() {
        return new AppInterceptor();
    }

    @Bean
    public ApiArgumentsResolver myResolver() {
        return new ApiArgumentsResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor()).excludePathPatterns(Constant.excludePathPatterns);
        super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(myResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}
