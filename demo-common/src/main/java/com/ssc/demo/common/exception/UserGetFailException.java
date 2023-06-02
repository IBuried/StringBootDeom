package com.ssc.demo.common.exception;


import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.core.ResultGenerator;
import com.ssc.demo.common.enums.ErrorEnum;

/**
 * @author: fearless
 * @description: 无法获取用户数据错误
 * @date: 2018-01-03 14:54:08
 */
public class UserGetFailException extends RuntimeException {
    private final Result result;

    /**
     * 调用时可以在任何代码处直接throws这个Exception,
     * 都会统一被拦截,并封装好json返回给前台
     *
     * @param errorEnum 以错误的ErrorEnum做参数
     */
    public UserGetFailException(ErrorEnum errorEnum) {
        this.result = ResultGenerator.genFailResult(ErrorEnum.E_1004);
    }

    public UserGetFailException(Result result) {
        this.result = result;
    }

    public Result getResultJson() {
        return result;
    }
}
