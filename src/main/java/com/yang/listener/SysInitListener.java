package com.yang.listener;

import com.yang.settings.entity.DicValue;
import com.yang.settings.service.DicService;
import com.yang.settings.service.impl.DicServiceImpl;
import com.yang.utils.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    DicService dicService;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("config/applicationContext.xml");
        dicService = (DicService) ac.getBean("dicService");
        ServletContext application = sce.getServletContext();
        Map<String, List<DicValue>> map = dicService.getAll();
        System.out.println(map);
        Set<String> set = map.keySet();
        for(String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");
        //处理完毕后,处理stage2possibility文件
        //解析文件
        Map<String,String> pMap = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle("config/Stage2Possibility");
        Enumeration<String> e = bundle.getKeys();
        while(e.hasMoreElements()){
            String key = e.nextElement();
            String value = bundle.getString(key);
            pMap.put(key,value);
        }
        application.setAttribute("pMap",pMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
