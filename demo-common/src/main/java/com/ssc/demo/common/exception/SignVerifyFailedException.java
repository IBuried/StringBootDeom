package com.ssc.demo.common.exception;

/**
 * @author Administrator
 * @create 2020/04/01 17:14
 */
public class SignVerifyFailedException extends RuntimeException {
    public SignVerifyFailedException(String message) {
        super(message);
    }

    public SignVerifyFailedException() {}
}
