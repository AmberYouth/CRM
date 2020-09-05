package com.yang.workbench.dao;

import com.yang.workbench.entity.Activity;

import java.util.HashMap;
import java.util.List;

public interface ActivityDao {

    Boolean save(Activity activity);

    List<Activity> getActivityListByCondition(HashMap<String, Object> activities);

    int getTotalByCondition(HashMap<String, Object> activities);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueid);

    List<Activity> getActivityListByName(String name);
}
