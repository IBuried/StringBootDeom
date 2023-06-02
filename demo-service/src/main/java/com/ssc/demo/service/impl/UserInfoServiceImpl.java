package com.ssc.demo.service.impl;


import com.ssc.demo.common.core.AbstractService;
import com.ssc.demo.db.UserInfo;
import com.ssc.demo.jdbc.UserInfoMapper;
import com.ssc.demo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Administrator on 2023/06/01.
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper mcUserInfoMapper;

    @Override
    public List<UserInfo> findByPhoneNum(String phoneNum) {
        return mcUserInfoMapper.selectByPhoneNum(phoneNum);
    }

    @Override
    public UserInfo findByOpenId(String openId) {
        return mcUserInfoMapper.selectByOpenId(openId);
    }
}
