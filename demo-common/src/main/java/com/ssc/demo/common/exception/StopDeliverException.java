package com.ssc.demo.common.exception;


import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.core.ServiceException;

/**
 * 类说明 （必填）
 * @author Administrator
 * @date 2022/04/14 17:53
 * @change 2022/04/14 17:53 Administrator@v1.0 创建 
 */
public class StopDeliverException extends ServiceException {
    public StopDeliverException(Result<Object> result) {
        super(result);
    }

    public StopDeliverException(String message) {
        super(message);
    }

    public StopDeliverException(String message, Throwable cause) {
        super(message, cause);
    }
}
