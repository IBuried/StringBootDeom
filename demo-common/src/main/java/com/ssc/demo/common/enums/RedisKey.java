package com.ssc.demo.common.enums;

/**
 * @author liaoyz
 */
public enum RedisKey {
    /**
     * 缓存key
     */
    // 用户openId
    EU_OPEN_ID("euOpenId"),
    // 应用名称
    INIT_APPLET_NAME_TO_SAAS("initAppletNameToSaas"),
    //用户端token 参数token
    ACCESS_TOKEN("accessToken:%s"),
    //管理端token 参数token
    ACCESS_TOKEN_ADMIN("accessTokenAdmin:%s"),
    //管理端用户id 参数最新token
    ACCESS_LATEST_TOKEN_ADMIN("accessLatestTokenAdmin:%s"),
    //微信登录sessionKey缓存 参数 微信登录jsCode
    WX_SESSION_KEY("wxSessionKey:%s"),

    ;

    private final String format;

    RedisKey(String format) {
        this.format = format;
    }

    public String key(Object... args) {
        if (args.length == 0) {
            return format;
        }
        return String.format(format, args);
    }
}
