package com.yang.settings.dao;

import com.yang.settings.entity.User;

import java.util.Map;

public interface UserDao {
    User login(Map<String,String> map);
}
