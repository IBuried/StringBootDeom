package com.ssc.demo.service;


import com.alibaba.fastjson.JSONObject;
import com.ssc.demo.common.core.Service;
import com.ssc.demo.db.UserInfo;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.List;

/**
 * Created by Administrator on 2023/06/01.
 */
public interface UserInfoService extends Service<UserInfo> {


    JSONObject appletLogin(JSONObject data) throws WxErrorException;


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
