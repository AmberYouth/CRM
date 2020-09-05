package com.yang.workbench.service;

import com.yang.settings.entity.User;
import com.yang.vo.PaginationVo;
import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.ActivityRemark;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface ActivityService {
    List<User> getUserList();
    boolean save(Activity activity);

    PaginationVo<Activity> pageList(HashMap<String, Object> activities);

    Boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<Activity> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByClueId(String clueid);

    List<Activity> getActivityListByName(String name);
}
