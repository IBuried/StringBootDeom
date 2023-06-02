package com.ssc.demo.common.exception;

/**
 * 类说明 （必填）
 *
 * @author Administrator
 * @version v1.0
 * @date 2021/11/23 16:12
 * @change 2021/11/23 16:12 Administrator@v1.0 创建
 */
public class WxApiException extends RuntimeException {
    public WxApiException(String message) {
        super(message);
    }
}
