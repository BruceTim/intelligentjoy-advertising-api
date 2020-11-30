package com.intelligentjoy.advertising.api.base.interfacees;

import com.intelligentjoy.advertising.api.base.model.Role;

import java.util.List;

public interface RoleService extends BaseService<Role>{

    List<Role> getRolesByUserId(Integer userId);

}
