package com.ssc.demo.common.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 无id的表
 */
public interface ServiceWithoutId<T> {
    void save(T model);//持久化
    void save(List<T> models);//批量持久化
    T findBy(String fieldName, Object value) throws TooManyResultsException; //通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
    List<T> findByCondition(Condition condition);//根据条件查找
    List<T> findAll();//获取所有
}
