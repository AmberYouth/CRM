package com.yang.settings.service;

import com.yang.exception.LoginException;
import com.yang.settings.entity.User;

public interface MyService {
    public User login(String loginAct,String loginPwd,String ip) throws LoginException;


}
