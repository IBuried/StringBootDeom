package com.ssc.demo.common.exception;


import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.core.ResultGenerator;
import com.ssc.demo.common.core.ServiceException;
import com.ssc.demo.common.enums.ErrorEnum;
import com.ssc.demo.common.enums.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Administrator
 * @description: 统一异常拦截
 * @date: 2018-01-03 14:54:15
 * @change: 2019-6-10 不能暴露具体的错误信息给客户端
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger("GlobalExceptionHandler");

    @ExceptionHandler(value = Exception.class)
    public Result<Object> defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("全局异常捕获", e);
        return new Result<>()
                .setCode(ResultCode.FAIL)
                .setMessage(ErrorEnum.E_00000.getErrorMsg());

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> missingServletRequestParameterException(MissingServletRequestParameterException e) throws Exception {
        //缺少字段
        return new Result<>().setCode(ResultCode.FAIL).setMessage(ErrorEnum.E_90003.getErrorMsg() + " " + e.getParameterName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) throws Exception {
        // 参数格式异常
        return new Result<>().setCode(ResultCode.FAIL).setMessage(ErrorEnum.E_90002.getErrorMsg());
    }

    /**
     * GET/POST请求方法错误的拦截器
     * 因为开发时可能比较常见,而且发生在进入controller之前,上面的拦截器拦截不到这个错误
     * 所以定义了这个拦截器
     *
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> httpRequestMethodHandler() {
        return ResultGenerator.genFailResult(ErrorEnum.E_500);
    }

    /**
     * 自定义错误的拦截器
     * 拦截到此错误之后,就返回这个类里面的json给前端
     * 常见使用场景是参数校验失败,抛出此错,返回错误信息给前端
     *
     * @param commonJsonException
     * @return
     */
    @ExceptionHandler(CommonJsonException.class)
    public Result<Object> commonJsonExceptionHandler(CommonJsonException commonJsonException) {
        return commonJsonException.getResultJson();
    }

    /**
     * 业务处理异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result<Object> serviceExceptionHandler(ServiceException e) {
        Result<Object> result = e.getResult();
        if (result != null) {
            return result;
        }
        return ResultGenerator.genFailResult(e.getMessage());
    }

    /**
     * 接口调用签名验证失败
     */
    @ExceptionHandler(SignVerifyFailedException.class)
    public Result<Object> signVerifyFailedException() {
        return ResultGenerator.genFailResult(ErrorEnum.E_1003);
    }

    /**
     * 数据结构异常
     */
    @ExceptionHandler(DataNonConformityException.class)
    public Result<Object> dataNonConformityException() {
        return ResultGenerator.genFailResult(ErrorEnum.E_1002);
    }

    /**
     * 登录过期
     */
    @ExceptionHandler(UserGetFailException.class)
    public Result<Object> userGetFailException(UserGetFailException e) {
        return e.getResultJson();
    }




    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<ObjectError> list = result.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : list) {
            sb.append(error.getDefaultMessage()).append(";");
        }
        return ResultGenerator.genFailResult(sb.toString());
    }


    /**
     * 参数非空校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> constraintViolationException(ConstraintViolationException e) {
        for (ConstraintViolation<?> s : e.getConstraintViolations()) {
            return ResultGenerator.genFailResult(s.getMessage());
        }
        return ResultGenerator.genFailResult("请求参数不合法");
    }


}
