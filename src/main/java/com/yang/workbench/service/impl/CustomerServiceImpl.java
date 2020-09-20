package com.yang.workbench.service.impl;

import com.yang.workbench.dao.CustomerDao;
import com.yang.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {
        System.out.println("进入模糊查询界面");
        List<String> sList = customerDao.getCustomerName(name);


        return sList;
    }
}
