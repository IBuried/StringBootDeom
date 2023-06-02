package com.ssc.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssc.demo.common.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TimeZone;

/**
 * 类说明 （必填）
 *
 * @author Administrator
 * @version v1.0
 * @date 2021/07/08 10:06
 * @change 2021/07/08 10:06 Administrator@v1.0 创建
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logAndPrepareInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(userValidationInterceptor())
                .addPathPatterns("/**")
                // swagger------------开始
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/api-docs")
                // swagger------------结束
                // 登录
                .excludePathPatterns("/login/into")
                // 测试
                .excludePathPatterns("/user/list")

        ;
    }

    private HandlerInterceptor userValidationInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                //登录权限认证
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            }
        };
    }

    private HandlerInterceptor logAndPrepareInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                //回去当前登录用户信息
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                // 清除用户信息缓存
            }
        };
    }

    /**
     * 错误的返回结果
     */
    private void falseResult(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             String msg, String ipAddr, int resultCode) {
        log.warn("签名认证失败，{}，请求接口：{}，请求IP：{}，请求参数：{}", msg,
                httpServletRequest.getRequestURI(), ipAddr, JSONObject.toJSONString(httpServletRequest.getParameterMap()));
        Result<Object> result = new Result<>();
        result.setCode(resultCode).setMessage(msg);
        responseResult(httpServletResponse, result);
    }

    public static void responseResult(HttpServletResponse response, Result<Object> result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。
        // Include.NON_NULL 属性为NULL 不序列化,就是为null的字段不参加序列化
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.setObjectMapper(objectMapper);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }
}
