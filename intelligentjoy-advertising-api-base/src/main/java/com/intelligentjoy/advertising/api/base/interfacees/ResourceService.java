package com.intelligentjoy.advertising.api.base.interfacees;

import com.intelligentjoy.advertising.api.base.model.Resource;

import java.util.List;

public interface ResourceService extends BaseService<Resource>{

    List<Resource> getResourcesByRoleId(Integer roleId);

    List<Resource> getResourcesByUserId(Integer userId);

    List<Resource> getResourceTreeByUserId(Integer userId);

}
