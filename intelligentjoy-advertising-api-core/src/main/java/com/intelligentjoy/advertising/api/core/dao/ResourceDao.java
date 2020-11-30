package com.intelligentjoy.advertising.api.core.dao;

import com.intelligentjoy.advertising.api.base.model.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceDao extends Mapper<Resource> {

    List<Resource> getResourcesByRoleId(@Param("roleId") Integer roleId);

    List<Resource> getResourcesByUserId(@Param("userId") Integer userId);

}