package com.ssc.demo.config;

import com.ssc.demo.common.swagger.ApiVersion;
import com.ssc.demo.common.swagger.ApiVersionEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Optional;

/**
 * 类说明 （必填）
 * @author Administrator
 * @date 2022/04/02 18:12
 * @change 2022/04/02 18:12 Administrator@v1.0 创建 
 */
@Component
public class AddMyBeanFactory implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (ApiVersionEnum version : ApiVersionEnum.values()) {
            if (version.sysType() != 1) {
                continue;
            }
            beanFactory.registerSingleton(version.v(), generateAppDocket(version));
        }
    }

    public Docket generateAppDocket(ApiVersionEnum versionEnum) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(ADMIN_API_INFO)
                .groupName(versionEnum.v())
                .select()
                .apis(input -> {
                    // 从方法中获取
                    Optional<ApiVersion> annotation = input.findAnnotation(ApiVersion.class);
                    if (!annotation.isPresent()) {
                        // 方法中不存在，则从类中获取
                        annotation = input.findControllerAnnotation(ApiVersion.class);
                        if (!annotation.isPresent()){
                            // 类中也没有
                            return false;
                        }
                    }
                    ApiVersion apiVersion = annotation.get();
                    return Arrays.asList(apiVersion.group()).contains(versionEnum);
                })//controller路径
                .paths(PathSelectors.any())
                .build();
    }

    protected static final ApiInfo ADMIN_API_INFO = new ApiInfoBuilder()
            .title("SpringBootDemo管理端")
            .description("SpringBootDemo")
            .termsOfServiceUrl("https://www.spring.io/")
            .version("1.0")
            .build();
}
