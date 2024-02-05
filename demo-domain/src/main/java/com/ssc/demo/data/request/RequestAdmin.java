package com.ssc.demo.data.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @description:
 * @author: LiuDan
 * @date: 2024/2/5 14:19
 * @version: v1.0
 * @change: [修改时间] [修改者]@[版本号] [修改内容说明]
 */
public class RequestAdmin extends RequestBase {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 账户手机号
     */
    private String phoneNum;
    /**
     * 账号昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        JSONObject temp = JSON.parseObject(JSON.toJSONString(this));
        for (Field field : RequestBase.class.getFields()) {
            temp.remove(field.getName());
        }
        return temp.toString();
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
