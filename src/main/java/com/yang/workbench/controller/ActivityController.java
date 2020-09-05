package com.yang.workbench.controller;

import com.yang.exception.LoginException;
import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.settings.service.MyService;
import com.yang.utils.DateTimeUtil;
import com.yang.utils.MD5Util;
import com.yang.utils.PrintJson;
import com.yang.utils.UUIDUtil;
import com.yang.vo.PaginationVo;
import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.ActivityRemark;
import com.yang.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ActivityController {
    @Autowired
    @Qualifier("activityService")
    private ActivityService service;

    @RequestMapping("/workbench/activity/getUserList.do")
    public void doMyServlet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动控制器");
        List<User> users = service.getUserList();
        PrintJson.printJsonObj(response, users);
    }

    @RequestMapping("/workbench/activity/save.do")
    public void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的添加操作");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Activity activity = new Activity();
        activity.setCost(cost);
        activity.setCreateBy(createBy);
        activity.setStartDate(startDate);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setEndDate(endDate);
        activity.setId(id);
        activity.setName(name);
        activity.setOwner(owner);
        System.out.println(activity);
        boolean flag = service.save(activity);

        PrintJson.printJsonFlag(response, flag);
    }

    @RequestMapping("/workbench/activity/pageList.do")
    public void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询和分页查询");
        HashMap<String, Object> activities = new HashMap();
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        activities.put("id", id);
        activities.put("name", name);
        activities.put("pageNo", pageNo);
        activities.put("owner", owner);
        activities.put("startDate", startDate);
        activities.put("name", name);
        activities.put("endDate", endDate);
        Integer skipCount = (Integer.valueOf(pageNo) - 1) * Integer.valueOf(pageSize);
        activities.put("skipCount", skipCount);
        activities.put("pageSize", Integer.valueOf(pageSize));
        System.out.println(activities);
        PaginationVo<Activity> vo = service.pageList(activities);
        PrintJson.printJsonObj(response, vo);
    }

    @RequestMapping("/workbench/activity/delete.do")
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        String ids[] = request.getParameterValues("id");
        Boolean flag = service.delete(ids);
        PrintJson.printJsonFlag(response, flag);
    }

    @RequestMapping("/workbench/activity/getUserListAndActivity.do")
    public void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询用户信息列表和根据市场活动信息id查询单条记录的操作");
        String id = request.getParameter("id");
        Map<String, Object> map = service.getUserListAndActivity(id);
        PrintJson.printJsonObj(response, map);
    }

    @RequestMapping("/workbench/activity/update.do")
    public void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场修改的活动");
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        System.out.println("修改者："+editBy);
        Activity a = new Activity();
        a.setCost(cost);
        a.setEditBy(editBy);
        a.setStartDate(startDate);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEndDate(endDate);
        a.setId(id);
        a.setName(name);
        a.setOwner(owner);
        boolean flag = service.update(a);
        PrintJson.printJsonFlag(response, flag);

    }


    @RequestMapping("/workbench/activity/detail.do")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("detail");
        Activity a = service.detail(id);
        mv.addObject("a",a);
        System.out.println("**********************************************************************");
        System.out.println("a.id"+id);
        return mv;
    }

    @RequestMapping("/workbench/activity/getRemarkListByAid.do")
    public void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response){
        String activityId = request.getParameter("activityId");
        System.out.println("activityId"+activityId);
        List<Activity> arlist = service.getRemarkListByAid(activityId);
        System.out.println("arlist"+arlist);
        PrintJson.printJsonObj(response,arlist);

    }

    @RequestMapping("/workbench/activity/deleteRemark.do")
    public void deleteRemark(HttpServletRequest request, HttpServletResponse response){
        System.out.println("删除备注操作");
        String id = request.getParameter("id");
        boolean flag = service.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/workbench/activity/saveRemark.do")
    public void saveRemark(HttpServletRequest request, HttpServletResponse response){
        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id = UUIDUtil.getUUID();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String editFlag = "0";
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setActivityId(activityId);
        activityRemark.setCreateBy(createBy);
        activityRemark.setCreateTime(createTime);
        activityRemark.setId(id);
        activityRemark.setEditFlag(editFlag);
        activityRemark.setNoteContent(noteContent);
        boolean flag = service.saveRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        PrintJson.printJsonObj(response,map);
    }

    @RequestMapping("/workbench/activity/updateRemark.do")
    public void updateRemark(HttpServletRequest request, HttpServletResponse response){
        System.out.println("执行修改备注的操作");
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        String editFlag = "1";
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditFlag(editFlag);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditTime(editTime);
        boolean flag = service.updateRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        PrintJson.printJsonObj(response,map);

    }
}
