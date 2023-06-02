package com.ssc.demo.db;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 头像(微信头像)
     */
    @Column(name = "head_img")
    private String headImg;

    /**
     * 手机号
     */
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 状态（0：已注销，1：正常，2：已封禁 ）
     */
    private Integer status;

    /**
     * 性别(0未知，1男2，女)
     */
    private Integer gender;

    /**
     * 微信unionid
     */
    @Column(name = "union_id")
    private String unionId;


    /**
     * 小程序关联公众号的用户openid(用户必须关注公众号)
     */
    @Column(name = "public_open_id")
    private String publicOpenId;

    /**
     * 小程序openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 父级用户Id
     */
    @Column(name = "parent_id")
    private Long parentId;

}