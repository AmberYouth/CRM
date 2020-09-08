package com.yang.workbench.dao;

import com.yang.workbench.entity.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);
}
