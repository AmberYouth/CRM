package com.yang.workbench.dao;

import com.yang.workbench.entity.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);
    List<String> getCustomerName(String name);
    int save(Customer customer);
}
