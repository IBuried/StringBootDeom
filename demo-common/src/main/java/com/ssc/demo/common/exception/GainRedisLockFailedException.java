package com.ssc.demo.common.exception;


import com.ssc.demo.common.core.ServiceException;

/**
 * 获取分布式锁失败时异常
 * @author Administrator
 * @date 2022/04/13 15:00
 * @change 2022/04/13 15:00 Administrator@v1.0 创建 
 */
public class GainRedisLockFailedException extends ServiceException {
    public GainRedisLockFailedException(String message) {
        super(message);
    }
}
