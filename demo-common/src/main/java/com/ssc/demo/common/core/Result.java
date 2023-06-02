package com.ssc.demo.common.core;

import com.alibaba.fastjson.JSON;
import com.ssc.demo.common.enums.ErrorEnum;
import com.ssc.demo.common.enums.ResultCode;

/**
 * 统一API响应结果封装
 * @author Administrator
 */

public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }

    public Result(ErrorEnum errorEnum, T data) {
        this.code = errorEnum.getErrorCode();
        this.message = errorEnum.getErrorMsg();
        this.data = data;
    }

    public Result<T> setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
