package com.yang.workbench.service.impl;

import com.yang.utils.DateTimeUtil;
import com.yang.utils.UUIDUtil;
import com.yang.workbench.dao.CustomerDao;
import com.yang.workbench.dao.TranDao;
import com.yang.workbench.dao.TranHistoryDao;
import com.yang.workbench.entity.Customer;
import com.yang.workbench.entity.Tran;
import com.yang.workbench.entity.TranHistory;
import com.yang.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

@Service("tranServiceImpl")
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public boolean save(Tran t, String customerName) {
        boolean flag = true;
        Customer customer = customerDao.getCustomerByName(customerName);
        if(customer==null){

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(t.getCreateBy());
            customer.setCreateTime(t.getCreateTime());
            customer.setContactSummary(t.getContactSummary());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setOwner(t.getOwner());
            int count1 = customerDao.save(customer);
            if (count1!=1){
                flag = false;
            }
        }
        //将客户id封装进去
        t.setCustomerId(customer.getId());
        int count2 = tranDao.save(t);
        if (count2!=1){
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(t.getCreateBy());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setTranId(t.getId());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setStage(t.getStage());
        tranHistory.setCreateBy(t.getCreateBy());
        int count3 = tranHistoryDao.save(tranHistory);
        if(count3!=1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran tran = tranDao.detail(id);

        return tran;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String id) {
        List<TranHistory> thList = tranHistoryDao.getHistoryListByTranId(id);

        return thList;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;
        int count = tranDao.changeStage(t);
        if (count!=1){
            flag = false;
        }
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(t.getEditBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setTranId(t.getId());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setStage(t.getStage());
        int count2 = tranHistoryDao.save(tranHistory);
        if (count2!=1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        int total = tranDao.getTotal();
        List<Map<String,Object>> dataList = tranDao.getCharts();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }
}
