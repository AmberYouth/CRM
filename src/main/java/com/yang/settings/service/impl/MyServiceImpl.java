package com.yang.settings.service.impl;

import com.yang.exception.LoginException;
import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.settings.service.MyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyServiceImpl implements MyService {
    @Resource
    private UserDao userDao;

    @Override
    public User login(String loginAct,String loginPwd,String ip) throws LoginException {
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("账户密码错误");
        }
        String expireTime = user.getExpireTime();
        String currentTime = user.getCreateTime();
        if (expireTime.compareTo(currentTime)<0){
            throw new LoginException("账户已失效");
        }


        return user;
    }
}
