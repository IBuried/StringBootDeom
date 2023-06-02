package com.ssc.demo.common.exception;


import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.core.ResultGenerator;
import com.ssc.demo.common.enums.ErrorEnum;

/**
 * @author: wb
 * @description: 自定义错误类
 * 比如在校验参数时,如果不符合要求,可以抛出此错误类
 * 拦截器可以统一拦截此错误,将其中json返回给前端
 * @date: 2018-01-03 14:54:08
 */
public class CommonJsonException extends RuntimeException {
    private final Result<Object> result;

    /**
     * 调用时可以在任何代码处直接throws这个Exception,
     * 都会统一被拦截,并封装好json返回给前台
     *
     * @param errorEnum 以错误的ErrorEnum做参数
     */
    public CommonJsonException(ErrorEnum errorEnum) {
        this.result = ResultGenerator.genFailResult(errorEnum);
    }

    public CommonJsonException(Result<Object> result) {
        this.result = result;
    }

    public Result<Object> getResultJson() {
        return result;
    }
}
