package com.etrade.puggo.config;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"test", "dev", "local"})
public class SwaggerConfig implements WebMvcConfigurer {

    String token = "eyJhbGciOiJIUzI1NiJ9" +
        ".eyJtZXJjaGFudElkIjoyMSwiZmZOdW1iZXIiOjI3NjQxNjEsImV4cCI6MTYwNTQwNDgxMSwidXNlcklkIjozMH0.9sDIe9g4nPGbHiXTAVrp0kTe1zI27v_xWRmFiK3bO5c";

    @Bean
    public Docket createRestApi() {
        //添加head参数start
        List<Parameter> pars = new ArrayList<>();
        // tokenPar.name("token").defaultValue(token).description("令牌")
        //     .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        // pars.add(tokenPar.build());
        //添加head参数end

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            // .globalOperationParameters(pars)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(Predicates.not(PathSelectors.regex("/error.*")))// 错误路径不监控
            .paths(Predicates.not(PathSelectors.regex("/javabeat.*")))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Everything Trade API Doc")
            .description("This is a restful api document of ETrade.")
            .version("1.0")
            .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

