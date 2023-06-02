package com.ssc.demo.config.wx;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Administrator
 * @date: 2022/8/6 0006 9:48
 * @version: v1.0
 * @change: [修改时间] [修改者]@[版本号] [修改内容说明]
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
@AllArgsConstructor
@Slf4j
public class WxPayConfiguration {

    @Resource
    private WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.properties.getPartnerKey()));
        payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getCertPath()));
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        payConfig.setNotifyUrl(this.properties.getNotifyUrl());
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

}
