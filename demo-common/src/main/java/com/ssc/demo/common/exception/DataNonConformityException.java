package com.ssc.demo.common.exception;

/**
 * @ClassName DataNonConformityException
 * @Description 数据不规范异常
 * @Author Fearless
 * @Date 19-4-18 上午10:47
 * @Version 1.0
 **/
public class DataNonConformityException extends RuntimeException {
    public DataNonConformityException(String msg) {
        super(msg);
    }

    public DataNonConformityException() {
    }
}
