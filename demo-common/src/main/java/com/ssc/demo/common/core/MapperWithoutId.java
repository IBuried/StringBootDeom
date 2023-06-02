package com.ssc.demo.common.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 无主键表使用该接口，
 *
 */
public interface MapperWithoutId<T>
        extends
        BaseMapper<T>,
        ConditionMapper<T>,
        InsertListMapper<T> {
}
