package com.yang.workbench.dao;

import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAid(String[] ids);

    List<Activity> getRemarkListByAid(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
