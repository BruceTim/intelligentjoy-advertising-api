package com.intelligentjoy.advertising.api.core.service;

import com.intelligentjoy.advertising.api.base.interfacees.BaseServiceImpl;
import com.intelligentjoy.advertising.api.base.interfacees.RoleService;
import com.intelligentjoy.advertising.api.base.model.Role;
import com.intelligentjoy.advertising.api.core.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Mapper<Role> getDao() {
        return roleDao;
    }

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return roleDao.getRolesByUserId(userId);
    }
}
