package com.yang.workbench.controller;


import com.yang.settings.entity.User;
import com.yang.utils.DateTimeUtil;
import com.yang.utils.PrintJson;
import com.yang.utils.UUIDUtil;
import com.yang.workbench.entity.Activity;
import com.yang.workbench.entity.Clue;
import com.yang.workbench.entity.Tran;
import com.yang.workbench.service.ActivityService;
import com.yang.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ClueController {
    @Autowired
    @Qualifier("clueServiceImpl")
    private ClueService clueService;

    @Autowired
    @Qualifier("activityService")
    private ActivityService activityService;

    @RequestMapping("/workbench/clue/getUserList.do")
    private void getUserList(HttpServletRequest request, HttpServletResponse response){
        List<User> users = clueService.getUserList();
        PrintJson.printJsonObj(response,users);
    }

    @RequestMapping("/workbench/clue/save.do")
    public void save(HttpServletRequest request, HttpServletResponse response){
        System.out.println("执行线索添加操作");
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        System.out.println(appellation+"--------------------**************///////////////////////");
        Clue clue = new Clue();
        clue.setId(id);
        clue.setAddress(address);
        clue.setWebsite(website);
        clue.setState(state);
        clue.setSource(source);
        clue.setPhone(phone);
        clue.setOwner(owner);
        clue.setNextContactTime(nextContactTime);
        clue.setMphone(mphone);
        clue.setJob(job);
        clue.setFullname(fullname);
        clue.setEmail(email);
        clue.setCompany(company);
        clue.setCreateBy(createBy);
        clue.setDescription(description);
        clue.setCreateTime(createTime);
        clue.setContactSummary(contactSummary);
        clue.setAppellation(appellation);
        Boolean flag = clueService.save(clue);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/workbench/clue/detail.do")
    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id  = request.getParameter("id");
       Clue c = clueService.detail(id);
           request.setAttribute("c",c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    @RequestMapping("/workbench/clue/getActivityListByClueId.do")
    public void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response){
        String clueid = request.getParameter("clueId");
        List<Activity> alist = activityService.getActivityListByClueId(clueid);
        PrintJson.printJsonObj(response,alist);
    }

    @RequestMapping("/workbench/clue/unbund.do")
    public void unbund(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        boolean flag = clueService.unbund(id);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("/workbench/clue/getActivityListByNameAndNotByClueId.do")
    public void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response){
        System.out.println("小夫，我进来了++++++++++++++++++++++++++++++++++++++");
        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");
        Map<String,String> map = new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);
        List<Activity> activityList = clueService.getActivityListByNameAndNotByClueId(map);
        PrintJson.printJsonObj(response,activityList);
    }

    @RequestMapping("workbench/clue/bund.do")
    public void bund(HttpServletRequest request, HttpServletResponse response){
        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");
        boolean flag = clueService.bund(cid,aids);
        PrintJson.printJsonFlag(response,flag);
    }

    @RequestMapping("workbench/clue/getActivityListByName.do")
    public void getActivityListByName(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("aname");
        List<Activity> activityList = activityService.getActivityListByName(name);

        PrintJson.printJsonObj(response,activityList);
    }

    @RequestMapping("workbench/clue/convert.do")
    public void convert(HttpServletRequest request, HttpServletResponse response){
        Tran tran = null;
        String clueId = request.getParameter("clueId");
        String flag = request.getParameter("flag");
        if ("true".equals(flag)){

        }
    }

}
