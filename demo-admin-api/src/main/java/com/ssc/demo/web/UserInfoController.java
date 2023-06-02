package com.ssc.demo.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.core.ResultGenerator;
import com.ssc.demo.common.swagger.ApiVersion;
import com.ssc.demo.common.swagger.ApiVersionEnum;
import com.ssc.demo.db.UserInfo;
import com.ssc.demo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @date 2022/08/02
 */
@Api(tags = "抽奖活动管理")
@Slf4j
@RestController
@RequestMapping("/user")
@ApiVersion({ApiVersionEnum.ADMIN_1_0_0})
public class UserInfoController {

    @Resource
    private UserInfoService mcUserInfoService;


    @ApiOperation(value = "查询用户信息")
    @GetMapping("/list")
    public Result<PageInfo<UserInfo>> userList(@RequestParam(defaultValue = "1",required = false) Integer page,
                                               @RequestParam(defaultValue = "10",required = false) Integer size,
                                               @RequestParam(required = false) String phoneNum){
        PageHelper.startPage(page,size);
        List<UserInfo> userInfoList = mcUserInfoService.findByPhoneNum(phoneNum);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "添加用户信息")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody UserInfo userInfo){
        userInfo.setCreateTime(new Date());
        mcUserInfoService.save(userInfo);
        return ResultGenerator.genSuccessMessage();
    }

}
