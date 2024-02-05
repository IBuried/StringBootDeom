package com.ssc.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssc.demo.common.constants.SysConfig;
import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.enums.RedisKey;
import com.ssc.demo.common.utils.IpUtil;
import com.ssc.demo.data.request.AdminHolder;
import com.ssc.demo.data.request.RequestAdmin;
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
import java.util.concurrent.TimeUnit;

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
                RequestAdmin admin = AdminHolder.get();
                // 校验是否存在验证信息 access_token
                if (admin.getRequestAccessToken() == null) {
                    falseResult(request, response, "未获取到认证信息",
                            admin.getRequestAllIp(), 20011);
                    return false;
                }
                // 校验是否存在用户信息
                if (admin.getId() == null) {
                    falseResult(request, response, "认证信息无效或已过期",
                            admin.getRequestAllIp(), 20011);
                    return false;
                }
                // 延时
                stringRedisTemplate.expire(RedisKey.ACCESS_TOKEN_ADMIN.key(admin.getRequestAccessToken()),
                        SysConfig.tokenExpire,
                        TimeUnit.MINUTES);
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
                RequestAdmin admin = new RequestAdmin();
                String ips = IpUtil.ipAddrLog(request);
                admin.setRequestAllIp(ips);
                admin.setRequestIp(ips.split(",")[0]);
                String sessionId = request.getHeader("sessionId");
                if (sessionId == null) {
                    try {
                        sessionId = request.getSession().getId();
                    } catch (IllegalStateException e) {
                        return false;
                    }
                }
                admin.setRequestAccessToken(sessionId);
                // 尝试获取用户信息
                if (sessionId != null) {
                    String key = RedisKey.ACCESS_TOKEN_ADMIN.key(sessionId);
                    String adminInfo = stringRedisTemplate.opsForValue().get(key);
                    if (adminInfo != null) {
                        JSONObject js = JSON.parseObject(adminInfo);
                        admin.setId(js.getLong("id"));
                        admin.setNickname(js.getString("nickname"));
                        admin.setPhoneNum(js.getString("phoneNum"));
                        admin.setAvatarUrl(js.getString("avatarUrl"));
                        // 登录用户最新token存入redis
                        String LatestKey = RedisKey.ACCESS_LATEST_TOKEN_ADMIN.key(js.getLong("id"));
                        stringRedisTemplate.opsForValue().set(LatestKey, sessionId, SysConfig.tokenExpire,
                                TimeUnit.MINUTES);
                    }
                } else {
                    System.err.println("sessionId为空");
                }
                AdminHolder.set(admin);
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
        result.setCode(resultCode).setMsg(msg);
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
