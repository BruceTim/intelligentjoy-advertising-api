package com.intelligentjoy.advertising.api.base.interfacees;

import com.intelligentjoy.advertising.api.base.model.User;

public interface UserService extends BaseService<User> {

    User getUserByName(String name);

    User getUserByPhone(String phoneAreaCode, String phone);

    User getUserByEmail(String name);

}
