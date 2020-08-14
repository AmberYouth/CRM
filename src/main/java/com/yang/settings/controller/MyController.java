package com.yang.settings.controller;

import com.yang.settings.entity.User;
import com.yang.settings.service.MyService;
import com.yang.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MyController {
    @Resource
    private MyService service;
    @RequestMapping("/login.do")
    public void doMyServlet(HttpServletRequest request,HttpServletResponse response){
        System.out.println("进入了servlet");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        //接受浏览器的ip地址
        String ip = request.getRemoteAddr();
        User user = service.login(loginAct,loginPwd,ip);
        request.getSession().setAttribute("user",user);
    }

}
