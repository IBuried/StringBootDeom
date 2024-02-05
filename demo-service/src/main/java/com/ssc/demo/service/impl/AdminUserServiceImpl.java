package com.ssc.demo.service.impl;



import com.alibaba.fastjson.JSON;
import com.ssc.demo.common.constants.SysConfig;
import com.ssc.demo.common.core.AbstractService;
import com.ssc.demo.common.core.ServiceException;
import com.ssc.demo.common.enums.RedisKey;
import com.ssc.demo.data.login.AccountDTO;
import com.ssc.demo.db.AdminUser;
import com.ssc.demo.jdbc.AdminUserMapper;
import com.ssc.demo.service.AdminUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by LiuDan on 2023/07/10.
 */
@Service
@Transactional
public class AdminUserServiceImpl extends AbstractService<AdminUser> implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
    @Override
    public AccountDTO login(String loginName, String password, String sessionId) {
        // 1、获取账号
        AdminUser mcAccount = adminUserMapper.selectByUsername(loginName);
        if (mcAccount == null) {
            throw new ServiceException("账号【" + loginName + "】不存在");
        }
        if (!DigestUtils.md5Hex(password).equals(mcAccount.getPassword())) {
            throw new ServiceException("密码错误");
        }
        if (mcAccount.getEnable() != 1) {
            throw new ServiceException("该账号已停用！");
        }
        AccountDTO account =new AccountDTO();
        account.setAvatarUrl(mcAccount.getAvatarUrl());
        account.setName(mcAccount.getName());
        account.setPhoneNum(mcAccount.getPhoneNum());
        account.setAccount(mcAccount.getAccount());
        account.setId(mcAccount.getId());
        account.setSessionId(sessionId);
        String redisKey = RedisKey.ACCESS_TOKEN_ADMIN.key(sessionId);
        stringRedisTemplate.opsForValue().set(redisKey, JSON.toJSONString(account), SysConfig.tokenExpire,
                TimeUnit.MINUTES);
        return account;
    }
}
