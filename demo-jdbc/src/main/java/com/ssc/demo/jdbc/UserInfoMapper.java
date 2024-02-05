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


    /***
     * 根据unionId查询用户信息
     * @param unionId
     * @return {@link UserInfo}
     * @author: LiuDan
     * @date: 2022/08/04  18:05
     * @version: v1.0
     */
    UserInfo selectByUnionId(@Param("unionId") String unionId);

    /***
     * 根据Id修改用户openId
     * @param openId
     * @param id
     * @return {@link int}
     * @author: LiuDan
     * @date: 2022/08/04  18:20
     * @version: v1.0
     */
    int updateOpenIdById(@Param("openId") String openId, @Param("id") Long id);



}