package com.yang.workbench.service;

import com.yang.settings.entity.User;
import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.Clue;
import com.yang.workbench.entity.Tran;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> getUserList();

    Boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    boolean bund(String cid, String[] aids);


    boolean convert(String clueId, Tran t, String createBy);
}
