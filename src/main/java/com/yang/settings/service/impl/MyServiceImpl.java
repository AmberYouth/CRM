package com.yang.settings.service.impl;

import com.yang.exception.LoginException;
import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.settings.service.MyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MyServiceImpl implements MyService {
    @Resource
    private UserDao userDao;

    @Override
    public User login(String loginAct,String loginPwd,String ip) throws LoginException {
        User user = new User();


        return user;
    }
}
