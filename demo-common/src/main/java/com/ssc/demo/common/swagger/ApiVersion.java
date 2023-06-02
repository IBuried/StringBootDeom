package com.ssc.demo.common.swagger;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类说明 （必填）
 * @author Administrator
 * @date 2022/04/02 17:58
 * @change 2022/04/02 17:58 Administrator@v1.0 创建 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface ApiVersion {
    /**
     * 接口版本号(对应swagger中的group)
     * @return String[]
     */
    @AliasFor("value")
    ApiVersionEnum[] group() default {};
    @AliasFor("group")
    ApiVersionEnum[] value() default {};
}
