package com.jf.system.swagger;

import com.jf.system.conf.SysConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Swagger配置
 * User: xujunfei
 * Date: 2018-06-26
 * Time: 15:09
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket api() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        // headers
        ParameterBuilder param1 = new ParameterBuilder();
        ParameterBuilder param2 = new ParameterBuilder();
        param1.name(SysConfig.TOKEN).description("Token")
                .modelRef(new ModelRef("string")).parameterType("header").required(false);
        param2.name("Req-Type").description("Req-Type")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("APP").required(true);
        parameters.add(param1.build());
        parameters.add(param2.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jf.controller")) // controller扫描路径
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api文档")
                .description("RESTFUL接口")
                .version("1.0")
                .build();
    }

}
