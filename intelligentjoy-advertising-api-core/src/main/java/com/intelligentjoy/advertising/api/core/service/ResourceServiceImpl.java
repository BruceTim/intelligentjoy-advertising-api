package com.intelligentjoy.advertising.api.core.service;

import com.intelligentjoy.advertising.api.base.interfacees.BaseServiceImpl;
import com.intelligentjoy.advertising.api.base.interfacees.ResourceService;
import com.intelligentjoy.advertising.api.base.model.Resource;
import com.intelligentjoy.advertising.api.base.model.tree.TreeModel;
import com.intelligentjoy.advertising.api.core.dao.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Mapper<Resource> getDao() {
        return resourceDao;
    }

    @Override
    public List<Resource> getResourcesByRoleId(Integer roleId) {
        return resourceDao.getResourcesByRoleId(roleId);
    }

    @Override
    public List<Resource> getResourcesByUserId(Integer userId) {
        return resourceDao.getResourcesByUserId(userId);
    }

    @Override
    public List<Resource> getResourceTreeByUserId(Integer userId) {
        return new TreeModel(getResourcesByUserId(userId)).buildTree();
    }
}
