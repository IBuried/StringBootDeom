package com.ssc.demo.jdbc;


import com.ssc.demo.common.core.Mapper;
import com.ssc.demo.db.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {


    /***
     * 根据openId查询用户信息
     * @param openId
     * @return {@link UserInfo}
     * @date: 2022/08/04  18:04
     * @version: v1.0
     */
    UserInfo selectByOpenId(@Param("openId") String openId);

    /**
     * 根据电话号码查询用户信息-可模糊查询
     * @return {@link List< UserInfo>}
     * @date: 2022/09/05  下午 04:13
     * @version: v1.0
     */
    List<UserInfo> selectByPhoneNum(@Param("phoneNum") String phoneNum);



}