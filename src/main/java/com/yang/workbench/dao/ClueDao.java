package com.yang.workbench.dao;


import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue clue);

    Clue detail(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    Clue getById(String clueId);

    int delete(String clueId);
}
