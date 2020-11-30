package com.intelligentjoy.advertising.api.base.interfacees;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public abstract Mapper<T> getDao();

    @Override
    public T selectByPrimaryKey(Object id) {
        return this.getDao().selectByPrimaryKey(id);
    }

    @Override
    public List<T> select(T t) {
        return this.getDao().select(t);
    }

    @Override
    public List<T> selectAll() {
        return this.getDao().selectAll();
    }

    @Override
    public T selectOne(T t) {
        return this.getDao().selectOne(t);
    }

    @Override
    public int insert(T t) {
        return this.getDao().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return this.getDao().insertSelective(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return this.getDao().updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return this.getDao().updateByPrimaryKeySelective(t);
    }

    @Override
    public int deleteByPrimaryKey(Object id) {
        return this.getDao().deleteByPrimaryKey(id);
    }

    @Override
    public int delete(T t) {
        return this.getDao().delete(t);
    }
}
