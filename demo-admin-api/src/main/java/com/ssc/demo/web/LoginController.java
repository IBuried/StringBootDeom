package com.ssc.demo.web;

import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.core.ResultGenerator;
import com.ssc.demo.common.swagger.ApiVersion;
import com.ssc.demo.common.swagger.ApiVersionEnum;
import com.ssc.demo.data.login.AccountDTO;
import com.ssc.demo.data.login.AdminLoginVO;
import com.ssc.demo.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: LiuDan
 * @date: 2024/2/5 14:24
 * @version: v1.0
 * @change: [修改时间] [修改者]@[版本号] [修改内容说明]
 */
@Api(tags = "后台登录类")
@RestController
@RequestMapping("/login")
@Slf4j
@ApiVersion({ApiVersionEnum.ADMIN_1_0_0})
public class LoginController {


    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/into")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录名", paramType = "body", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "body", required = true)
    })
    public Result<AccountDTO> login(@RequestBody AdminLoginVO adminLoginVO,
                                    HttpServletRequest request) {
        String loginName = adminLoginVO.getLoginName();
        String password = adminLoginVO.getPassword();
        if (loginName==null) {
            return ResultGenerator.genFailResult("登录名不为空");
        }
        if (password==null) {
            return ResultGenerator.genFailResult("密码不为空");
        }
        String sessionId = request.getSession().getId();
        AccountDTO sysAdmin = adminUserService.login(loginName, password, sessionId);
        return ResultGenerator.genSuccessResult(sysAdmin);
    }

}
