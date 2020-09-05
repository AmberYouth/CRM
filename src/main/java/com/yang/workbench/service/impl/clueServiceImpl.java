package com.yang.workbench.service.impl;

import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.utils.UUIDUtil;
import com.yang.workbench.dao.ClueActivityRelationDao;
import com.yang.workbench.dao.ClueDao;
import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.Clue;
import com.yang.workbench.entity.ClueActivityRelation;
import com.yang.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class clueServiceImpl implements ClueService {
    @Autowired
    @Qualifier("clueDao")
    ClueDao clueDao;
    @Resource
    UserDao userDao;
    @Resource
    ClueActivityRelationDao clueActivityRelationDao;
    @Override
    public List<User> getUserList() {
        List<User> users = userDao.getUserList();
        return users;
    }

    @Override
    public Boolean save(Clue clue) {
        boolean flag = true;
        int count = clueDao.save(clue);
        if (count!=1){
            flag =false;
        }

        return flag;
    }

    @Override
    public Clue detail(String id) {
        boolean flag = true;
        Clue c = clueDao.detail(id);
        return c;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag = false;
        int count = clueActivityRelationDao.unbund(id);
        if (count==1){
            flag=true;
        }



        return flag;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> activityList = clueDao.getActivityListByNameAndNotByClueId(map);
        return activityList;
    }

    @Override
    public boolean bund(String cid, String[] aids) {
        boolean flag = true;
        for(String aid:aids){
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);

            int count = clueActivityRelationDao.bund(car);
            if (count!=1){
                return false;
            }
        }

        return flag;
    }
}
