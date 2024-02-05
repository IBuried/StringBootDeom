package com.ssc.demo.data.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: liuDan
 * @date: 2022/8/3 0003 15:43
 * @version: v1.0
 * @change: [修改时间] [修改者]@[版本号] [修改内容说明]
 */
@ApiModel("管理后台登录参数")
@Data
public class AdminLoginVO {

    @ApiModelProperty(value = "登录名")
    @NotNull(message = "缺少必要参数")
    private String loginName;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "缺少必要参数")
    private String password;
}
