package com.intelligentjoy.advertising.api.core.service;

import com.intelligentjoy.advertising.api.base.interfacees.BaseServiceImpl;
import com.intelligentjoy.advertising.api.base.interfacees.UserService;
import com.intelligentjoy.advertising.api.base.model.User;
import com.intelligentjoy.advertising.api.core.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Mapper<User> getDao() {
        return userDao;
    }

    @Override
    public User getUserByName(String name) {
        return null;
    }

    @Override
    public User getUserByPhone(String phoneAreaCode, String phone) {
        return null;
    }

    @Override
    public User getUserByEmail(String name) {
        return null;
    }
}
