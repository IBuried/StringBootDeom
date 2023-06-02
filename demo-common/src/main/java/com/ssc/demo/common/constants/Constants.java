package com.ssc.demo.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: wb
 * @description: 通用常量类, 单个业务的常量请单开一个类, 方便常量的分类管理
 * @date: 2018/1/3
 */
@Component
public class Constants {

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MSG = "请求成功";

    /**
     * session中存放用户信息的key值
     */
    public static String SESSION_USER_INFO;

    @Value("${key.userInfo:#{null}}")
    public void setSessionUserInfo(String sessionUserInfo) {
        SESSION_USER_INFO = sessionUserInfo;
    }

}
