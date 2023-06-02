package com.ssc.demo.common.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 适用于没有id的表
 */
public abstract class AbstractServiceWithoutId<T> implements ServiceWithoutId<T> {

    @Autowired
    protected MapperWithoutId<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractServiceWithoutId() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }
    @Override
    public void save(T model) {
        mapper.insertSelective(model);
    }
    @Override
    public void save(List<T> models) {
        mapper.insertList(models);
    }


    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }
    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

}
