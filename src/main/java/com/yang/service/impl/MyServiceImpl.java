package com.yang.service.impl;

import com.yang.dao.StudentDao;
import com.yang.entity.Student;
import com.yang.service.MyService;
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
