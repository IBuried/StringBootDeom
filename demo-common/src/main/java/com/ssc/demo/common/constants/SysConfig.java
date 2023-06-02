package com.ssc.demo.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明 （必填）
 *
 * @author Administrator
 * @version v1.0
 * @date 2021/09/12 17:31
 * @change 2021/09/12 17:31 Administrator@v1.0 创建
 */
@Configuration
public class SysConfig {
    /**
     * 对称加密key
     */
    public static String aesKey;
    /**
     * token有效期(min)
     */
    public static Integer tokenExpire;
    /**
     * 系统运行环境
     */
    public static String env;

    @Value("${sys.aes-key:#{null}}")
    public void setAesKey(String aesKey) {
        SysConfig.aesKey = aesKey;
    }

    @Value("${sys.token.expire:#{null}}")
    public void setTokenExpire(Integer tokenExpire) {
        SysConfig.tokenExpire = tokenExpire;
    }

    @Value("${spring.profiles.active}")
    public void setEnv(String env) {
        SysConfig.env = env;
    }


    public static boolean envIsProd() {
        return "prod".equals(env);
    }
}
