package com.yang.settings.controller;

import com.yang.settings.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MyController {
    @RequestMapping("/myServlet.do")
    public void doMyServlet(HttpServletResponse response){
        System.out.println("进入了servlet");
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().print("{\"str1\":\"杨世杰\",\"num\":\"123\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/myServlet02.do")
    public void doMyServlet02(HttpServletResponse response){
        System.out.println("进入了servlet");
        response.setContentType("text/html;charset=UTF-8");
        Student s1 = new Student(1,"杨世杰",20);
        Student s2 = new Student(2,"杨森",18);
        try {
            response.getWriter().print("{\"stu1\":{\"id\":\""+s1.getId()
                                        +"\",\"name\":\""+s1.getName()
                                        +"\",\"age\":"+s1.getAge()+
                                        "},\"stu2\":{\"id\":\""+s2.getId()
                                        +"\",\"name\":\""+s2.getName()
                                        +"\",\"age\":"+s2.getAge()+"}}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
