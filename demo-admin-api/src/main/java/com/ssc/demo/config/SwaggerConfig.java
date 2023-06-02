package com.ssc.demo.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 */
@Configuration
@Profile({"dev","test"})
public class SwaggerConfig {

    /**
     * 配置content type
     */
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>() {{
                add("*/*");
            }};
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(AddMyBeanFactory.ADMIN_API_INFO)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();

    }

}