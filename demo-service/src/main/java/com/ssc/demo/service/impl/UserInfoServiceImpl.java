package com.ssc.demo.service.impl;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ssc.demo.common.constants.SysConfig;
import com.ssc.demo.common.core.AbstractService;
import com.ssc.demo.common.enums.RedisKey;
import com.ssc.demo.common.utils.UserTokenUtil;
import com.ssc.demo.db.UserInfo;
import com.ssc.demo.jdbc.UserInfoMapper;
import com.ssc.demo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2023/06/01.
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper mcUserInfoMapper;

    @Resource
    private WxMaService wxMaService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public JSONObject appletLogin(JSONObject data) throws WxErrorException {
        String opCode=data.getString("opCode");
        WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(opCode);
        String openId = session.getOpenid();
        String unionId = session.getUnionid();
        UserInfo user = selectUserByUnionIdOrOpenId(unionId, openId);
        boolean register = false;
        // 判断注册或更新
        if (user == null) {
            register = true;
            user = new UserInfo();
        }
        // 保存unionId
        user.setUnionId(unionId);
        user.setOpenId(openId);
        JSONObject object=new JSONObject();
        // 注册或更新操作
        if (register) {
            //插入用户信息默认值
            user.setGender(data.getInteger("gender"));
            user.setNickName(data.getString("nickName"));
            user.setHeadImg(data.getString("headImg"));
            user.setStatus(1);
            user.setCreateTime(new Date());
            mcUserInfoMapper.insert(user);
            String token = UserTokenUtil.generateToken(user.getId());
            object.put("token",token);
            object.put("nickName",data.getString("nickName"));
            object.put("headImg",data.getString("headImg"));
            return object;
        }
        // 更新
        mcUserInfoMapper.updateByPrimaryKeySelective(user);
        JSONObject userJson = JSONObject.parseObject(JSONObject.toJSONString(user));
        // 缓存当前登录openId
        userJson.put("currentOpenId", openId);
        Long userId = user.getId();
        String token = UserTokenUtil.generateToken(userId);
        userJson.put("token", token);
        stringRedisTemplate.opsForValue().set(RedisKey.ACCESS_TOKEN.key(userId), userJson.toJSONString(),
                SysConfig.tokenExpire, TimeUnit.MINUTES);
        object.put("token",token);
        object.put("nickName",data.getString("nickName"));
        object.put("headImg",data.getString("headImg"));
        return object;
    }




    @Override
    public List<UserInfo> findByPhoneNum(String phoneNum) {
        return mcUserInfoMapper.selectByPhoneNum(phoneNum);
    }

    @Override
    public UserInfo findByOpenId(String openId) {
        return mcUserInfoMapper.selectByOpenId(openId);
    }


    /***
     * 检查用户是否注册
     * @param unionId
     * @param openId
     * @return {@link UserInfo}
     * @author: LiuDan
     * @date: 2022/08/04  18:23
     * @version: v1.0
     */
    private UserInfo selectUserByUnionIdOrOpenId(String unionId, String openId) {
        UserInfo user;
        if (StrUtil.isBlank(unionId)) {
            user = mcUserInfoMapper.selectByOpenId(openId);
        } else {
            // 能获取到union_id
            user = mcUserInfoMapper.selectByUnionId(unionId);
            if (user == null) {
                // 获取到union_id，但union_id并未录入系统
                user = mcUserInfoMapper.selectByOpenId(openId);
            } else {
                // 更新openId
                Long userId = user.getId();
                if (!openId.equals(user.getOpenId())) {
                    mcUserInfoMapper.updateOpenIdById(openId, userId);
                }
            }
        }
        return user;
    }

}
