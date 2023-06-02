package com.ssc.demo.config.wx;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="wx.pay")
public class WxPayProperties {

    /**
     * 设置微信小程序的appid
     */
    @Value("${wx.ma.app-id}")
    private String appId;

    /**
     * 设置微信商户号的id
     */
    private String mchId;
    /**
     * 设置微信商户号的密钥
     */
    private String partnerKey;
    /**
     * 设置微信商户号的证书的路径
     */
    private String certPath;

    /**
     * 支付回调地址
     */
    private String notifyUrl;

}
