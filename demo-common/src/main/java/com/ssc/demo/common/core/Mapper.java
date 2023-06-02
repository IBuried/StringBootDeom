package com.ssc.demo.common.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 * 表中无主键会报如下错误,此时需要使用MapperWithoutId
 * tk.mybatis.mapper.MapperException: tk.mybatis.mapper.MapperException: 继承 deleteByIds 方法的实体类[className]中必须只有一个带有 @Id 注解的字段
 */
public interface Mapper<T>
        extends
        BaseMapper<T>,
        ExampleMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        InsertListMapper<T> {
}
