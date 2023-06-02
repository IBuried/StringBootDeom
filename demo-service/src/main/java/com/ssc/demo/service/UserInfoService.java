package com.ssc.demo.service;


import com.ssc.demo.common.core.Service;
import com.ssc.demo.db.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2023/06/01.
 */
public interface UserInfoService extends Service<UserInfo> {


    /**
     * 根据电话号码查询用户信息-可模糊查询
     * @param phoneNum
     * @return {@link List<UserInfo>}
     */
    List<UserInfo> findByPhoneNum(String phoneNum);


    /***
     * 根据openId查询用户信息
     * @param openId
     * @return {@link UserInfo}
     * @version: v1.0
     */
    UserInfo findByOpenId(String openId);

}
