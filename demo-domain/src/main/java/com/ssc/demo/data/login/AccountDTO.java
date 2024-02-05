package com.ssc.demo.data.login;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class AccountDTO {
    @ApiModelProperty("账号id")
    private Long id;


    @ApiModelProperty("名字")
    private String name;

    /**
     * 昵称
     */
    @ApiModelProperty("账号")
    private String account;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatarUrl;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phoneNum;

    /**
     * 会话Id
     */
    @ApiModelProperty("会话Id")
    private String sessionId;


}