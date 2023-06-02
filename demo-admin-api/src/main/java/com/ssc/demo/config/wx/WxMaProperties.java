package com.ssc.demo.config.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LEGION
 */
@Data
@ConfigurationProperties(prefix="wx.ma")
public class WxMaProperties {
    /**
     * 设置微信小程序的appid
     */
    private String appId;
    /**
     * 设置微信小程序的Secret
     */
    private String appSecret;
    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token;

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey;

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat;

}
