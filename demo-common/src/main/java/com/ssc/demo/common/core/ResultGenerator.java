package com.ssc.demo.common.core;


import com.ssc.demo.common.enums.ErrorEnum;
import com.ssc.demo.common.enums.ResultCode;

/**
 * 响应结果生成工具
 * @author Administrator
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 返回成功状态信息（用户增删改返回成功状态信息）
     *
     * @return
     */
    public static <T> Result<T> genSuccessMessage() {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS_CUD)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回成功状态信息（用户增删改返回成功状态信息）
     *
     * @return
     */
    public static <T> Result<T> genSuccessMessage(T data) {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS_CUD)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }


    public static <T> Result<T> genSuccessResult() {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static <T> Result<T> genFailResult(String message) {
        return new Result<T>()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }


    /**
     * @param code    错误状态码
     * @param message 错误信息
     * @return
     */
    public static <T> Result<T> genFailResult(int code, String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message);
    }

    /**
     * @param errorEnum 错误码枚举对象
     * @return
     */
    public static <T> Result<T> genFailResult(ErrorEnum errorEnum) {
        return new Result<T>()
                .setCode(errorEnum.getErrorCode())
                .setMessage(errorEnum.getErrorMsg());
    }

    /**
     * @param code    错误状态码
     * @param message 错误信息
     * @param data    返回数据对象
     * @return
     */
    public static <T> Result<T> genFailResult(int code, String message, T data) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

}
