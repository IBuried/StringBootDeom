package com.ssc.demo.service;

import com.ssc.demo.common.core.Service;
import com.ssc.demo.data.login.AccountDTO;
import com.ssc.demo.db.AdminUser;


import java.util.List;

/**
 * Created by LiuDan on 2023/07/10.
 */
public interface AdminUserService extends Service<AdminUser> {


    /***
     * 登录
     * @param loginName 登录名
     * @param password  密码
     * @param sessionId      * @param password  密码
     * @return {@link AccountDTO}
     * @author: LiuDan
     * @date: 2022/08/03  16:35
     * @version: v1.0
     */
    AccountDTO login(String loginName, String password, String sessionId);



}
