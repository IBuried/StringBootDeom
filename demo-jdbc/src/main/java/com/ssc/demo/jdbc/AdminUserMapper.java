package com.ssc.demo.jdbc;

import com.ssc.demo.common.core.Mapper;
import com.ssc.demo.db.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper extends Mapper<AdminUser> {


    AdminUser selectByUsername(@Param("account") String account);


}