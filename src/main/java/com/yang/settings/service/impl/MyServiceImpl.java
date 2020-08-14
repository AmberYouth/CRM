package com.yang.settings.service.impl;

import com.yang.settings.dao.StudentDao;
import com.yang.settings.entity.Student;
import com.yang.settings.service.MyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MyServiceImpl implements MyService {
    @Resource
    private StudentDao studentDao;
    @Override
    public Student FindStudentById() {
        Student stu = studentDao.selectStudentById();
        return stu;
    }
}
