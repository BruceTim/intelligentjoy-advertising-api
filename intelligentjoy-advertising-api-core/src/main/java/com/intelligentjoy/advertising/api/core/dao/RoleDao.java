package com.intelligentjoy.advertising.api.core.dao;

import com.intelligentjoy.advertising.api.base.model.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleDao extends Mapper<Role> {

    List<Role> getRolesByUserId(@Param("userId") Integer userId);

}