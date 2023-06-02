package com.ssc.demo.common.enums;

/**
 * @author: wb
 * @date: 2018/1/3
 */
public enum ErrorEnum {
    /*
     * 错误信息
     * */
    E_400(400, "请求处理异常，请稍后再试"),
    E_500(500, "请求方式有误,请检查 GET/POST"),
    E_501(501, "请求路径不存在"),
    E_502(502, "权限不足"),
    E_00000(00000,"操作频繁，请稍后再试"),
    E_1000(1000, "数据提交成功"),
    E_1001(1001, "请求超时"),
    E_1002(1002, "数据不符合规范"),
    E_1003(1003, "token验证失败"),
    E_90002(90002,"参数格式错误"),
    E_90003(90003,"缺少必填参数"),
    E_1004(1004, "token过期"),
        ;
    private Integer errorCode;

    private String errorMsg;

    ErrorEnum() {
    }

    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "错误 " + errorCode + " ：" + errorMsg;
    }
}
