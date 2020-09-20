package com.yang.workbench.controller;

import com.mysql.jdbc.TimeUtil;
import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.utils.DateTimeUtil;
import com.yang.utils.PrintJson;
import com.yang.utils.UUIDUtil;
import com.yang.workbench.entity.Tran;
import com.yang.workbench.service.CustomerService;
import com.yang.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Controller
public class TranController {
    @Autowired
    private TranService tranService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/workbench/transaction/add.do")
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转交易添加的操作");
        List<User> list = userDao.getUserList();
        request.setAttribute("uList",list);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }

    @RequestMapping("/workbench/transaction/getCustomerName.do")
    public void getCustomerName(HttpServletRequest request, HttpServletResponse response){
        //通过前段输入名称进行模糊查询
        System.out.println("通过前段输入名称进行模糊查询");
        String name = request.getParameter("name");
        List<String> sList = customerService.getCustomerName(name);
        PrintJson.printJsonObj(response,sList);
    }

    @RequestMapping("workbench.transaction/save.do")
    public void save(HttpServletRequest request, HttpServletResponse response){
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerId = request.getParameter("customerId");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createBy = request.getParameter("createBy");
        String createTime = ((User)request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        Tran t = new Tran();
        t.setType(type);
        t.setName(name);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setActivityId(activityId);
        t.setId(id);
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setSource(source);
        t.setOwner(owner);
        t.setNextContactTime(nextContactTime);
        t.setDescription(description);
        t.setCustomerId(customerId);
        t.setContactsId(contactsId);
        t.setContactSummary(contactSummary);

    }
}
