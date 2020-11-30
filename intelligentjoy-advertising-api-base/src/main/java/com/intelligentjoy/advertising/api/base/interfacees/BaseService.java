package com.intelligentjoy.advertising.api.base.interfacees;

import java.util.List;

public interface BaseService<T> {

    T selectByPrimaryKey(Object id);

    List<T> select(T t);

    List<T> selectAll();

    T selectOne(T t);

    int insert(T t);

    int insertSelective(T t);

    int updateByPrimaryKey(T t);

    int updateByPrimaryKeySelective(T t);

    int deleteByPrimaryKey(Object id);

    int delete(T t);
}
