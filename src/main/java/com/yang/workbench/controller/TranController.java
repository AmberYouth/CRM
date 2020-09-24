package com.yang.workbench.controller;

import com.mysql.jdbc.TimeUtil;
import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.utils.DateTimeUtil;
import com.yang.utils.PrintJson;
import com.yang.utils.UUIDUtil;
import com.yang.workbench.entity.Tran;
import com.yang.workbench.entity.TranHistory;
import com.yang.workbench.service.CustomerService;
import com.yang.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/workbench/transaction/save.do")
    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("我进入tran的页面啦");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");
        String customerId = request.getParameter("customerId");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
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
        System.out.println(contactsId+"我想看看contactsId");
        t.setContactSummary(contactSummary);
        boolean flag = tranService.save(t,customerName);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }
    }

    @RequestMapping("/workbench/transaction/detail.do")
    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");
        Tran t = tranService.detail(id);

        String stage = t.getStage();
        Map<String,String> pMap = (Map<String, String>) request.getSession().getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);
        request.setAttribute("t",t);
        System.out.println("我想查一下TRANID"+t.getId());
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);
    }

    @RequestMapping("/workbench/transaction/getHistoryListByTranId.do")
    public void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response){
        System.out.println("根据交易Id取得相应的历史列表");
        String id = request.getParameter("tranId");
        List<TranHistory> thList = tranService.getHistoryListByTranId(id);
        PrintJson.printJsonObj(response,thList);
    }
}
