package com.yang.workbench.service.impl;

import com.yang.exception.LoginException;
import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.vo.PaginationVo;
import com.yang.workbench.dao.ActivityDao;
import com.yang.workbench.dao.ActivityRemarkDao;
import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.ActivityRemark;
import com.yang.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    @Qualifier("userDao")
    UserDao dao;
    @Autowired
    @Qualifier("activityDao")
    ActivityDao activityDao;
    @Autowired
    @Qualifier("activityRemarkDao")
    ActivityRemarkDao activityRemarkDao;



    @Override
    public List<User> getUserList() {
        List<User> users = dao.getUserList();
        return users;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {
                    LoginException.class
            }
    )
    @Override
    public boolean save(Activity activity) {
        System.out.println("执行到了到了service层");
        Boolean flag = activityDao.save(activity);
        System.out.println("执行到了到了service层");

        return flag;
    }
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {
                    LoginException.class
            }
    )
    @Override
    public PaginationVo<Activity> pageList(HashMap<String, Object> activities) {
        int total = activityDao.getTotalByCondition(activities);
        System.out.println("我卡死在dao层了，教教我");
        System.out.println(activities);
        List<Activity> dataList = activityDao.getActivityListByCondition(activities);
        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setDataList(dataList);
        vo.setTotal(total);
        System.out.println(vo);
        System.out.println("===========================================");
        return vo;
    }
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {
                    Exception.class
            }
    )
    @Override
    public Boolean delete(String[] ids) {
        System.out.println("进入了delete方法");
        Boolean flag = true;
        int count1 = activityRemarkDao.getCountByAids(ids);
        System.out.println("count1"+count1);
        int count2 = activityRemarkDao.deleteByAid(ids);
        System.out.println("count2"+count2);
        int count3 = activityDao.delete(ids);
        if (count1!=count2){
            flag = false;
        }
        if (count3!=ids.length){
            flag = false;
        }
        return flag;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {
                    Exception.class
            }
    )
    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> userList = dao.getUserList();
        Activity activity = activityDao.getById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("uList",userList);
        map.put("a",activity);
        System.out.println(activity);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        int flag = -1;

        flag = activityDao.update(a);
        if (flag!=-1){
            return true;
        }

        return false;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = null;
        System.out.println("id="+id);
        activity = activityDao.detail(id);
        System.out.println("activity"+activity);
        return activity;
    }

    @Override
    public List<Activity> getRemarkListByAid(String activityId) {
        List<Activity> arlist = new ArrayList<>();
        arlist = activityRemarkDao.getRemarkListByAid(activityId);
        System.out.println(arlist);

        return arlist;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = activityRemarkDao.deleteById(id);
        if (count!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag  = true;
        int count = activityRemarkDao.saveRemark(activityRemark);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        Boolean flag = true;
        int n = activityRemarkDao.updateRemark(activityRemark);
        if (n!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueid) {
        List<Activity> alist = activityDao.getActivityListByClueId(clueid);


        return alist;
    }

    @Override
    public List<Activity> getActivityListByName(String name) {
        List<Activity> alist = activityDao.getActivityListByName(name);

        return alist;
    }
}
