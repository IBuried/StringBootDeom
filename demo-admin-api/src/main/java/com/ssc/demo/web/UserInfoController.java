package com.ssc.demo.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssc.demo.common.core.Result;
import com.ssc.demo.common.core.ResultGenerator;
import com.ssc.demo.db.UserInfo;
import com.ssc.demo.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @date 2022/08/02
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private UserInfoService mcUserInfoService;


    @GetMapping("/list")
    public Result<PageInfo<UserInfo>> userList(@RequestParam(defaultValue = "1",required = false) Integer page,
                                               @RequestParam(defaultValue = "10",required = false) Integer size,
                                               @RequestParam(required = false) String phoneNum){
        PageHelper.startPage(page,size);
        List<UserInfo> userInfoList = mcUserInfoService.findByPhoneNum(phoneNum);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody UserInfo userInfo){
        userInfo.setCreateTime(new Date());
        mcUserInfoService.save(userInfo);
        return ResultGenerator.genSuccessMessage();
    }

}
