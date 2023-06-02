package com.ssc.demo.common.core;


import com.ssc.demo.common.enums.ErrorEnum;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 *
 * @author Administrator
 */
public class ServiceException extends RuntimeException {
    private Result<Object> result;

    public ServiceException(Result<Object> result) {
        this.result = result;
    }

    public ServiceException(ErrorEnum error) {
        this.result = ResultGenerator.genFailResult(error);
    }

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public Result<Object> getResult() {
        return result;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
