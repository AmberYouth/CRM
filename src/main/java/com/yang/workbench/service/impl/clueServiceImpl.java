package com.yang.workbench.service.impl;

import com.yang.exception.LoginException;
import com.yang.settings.dao.UserDao;
import com.yang.settings.entity.User;
import com.yang.utils.DateTimeUtil;
import com.yang.utils.UUIDUtil;
import com.yang.workbench.dao.*;
import com.yang.workbench.entity.*;
import com.yang.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class clueServiceImpl implements ClueService {
    @Autowired
    @Qualifier("clueDao")
    ClueDao clueDao;
    @Resource
    UserDao userDao;
    @Resource
    ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    ClueRemarkDao clueRemarkDao;

    @Resource
    CustomerDao customerDao;
    @Resource
    CustomerRemarkDao customerRemarkDao;

    @Resource
    ContactsDao contactsDao;
    @Resource
    ContactsRemarkDao contactsRemarkDao;
    @Resource
    ContactsActivityRelationDao contactsActivityRelationDao;

    @Resource
    TranDao tranDao;
    @Resource
    TranHistoryDao tranHistoryDao;

    @Override
    public List<User> getUserList() {
        List<User> users = userDao.getUserList();
        return users;
    }

    @Override
    public Boolean save(Clue clue) {
        boolean flag = true;
        int count = clueDao.save(clue);
        if (count!=1){
            flag =false;
        }

        return flag;
    }

    @Override
    public Clue detail(String id) {
        boolean flag = true;
        Clue c = clueDao.detail(id);
        return c;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag = false;
        int count = clueActivityRelationDao.unbund(id);
        if (count==1){
            flag=true;
        }
        return flag;
    }
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {
                    LoginException.class
            }
    )
    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> activityList = clueDao.getActivityListByNameAndNotByClueId(map);
        return activityList;
    }
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {
                    LoginException.class
            }
    )
    @Override
    public boolean bund(String cid, String[] aids) {
        boolean flag = true;
        for(String aid:aids){
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);

            int count = clueActivityRelationDao.bund(car);
            if (count!=1){
                return false;
            }
        }

        return flag;
    }
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false,
            rollbackFor = {
                    RuntimeException.class
            }
    )
    @Override
    public boolean convert(String clueId, Tran t, String createBy) {
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = true;
        Clue clue = clueDao.getById(clueId);
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer==null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setOwner(clue.getOwner());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(clue.getCreateBy());
            customer.setDescription(clue.getDescription());
            //添加客户
            int count = customerDao.save(customer);
            if (count!=1){
                flag = false;
            }
        }

        Contacts con = new Contacts();
        con.setSource(clue.getSource());
        con.setOwner(clue.getOwner());
        con.setNextContactTime(clue.getNextContactTime());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setId(UUIDUtil.getUUID());
        con.setFullname(clue.getFullname());
        con.setEmail(clue.getEmail());
        con.setDescription(clue.getDescription());
        con.setCustomerId(customer.getId());
        con.setCreateTime(clue.getCreateTime());
        con.setCreateBy(clue.getCreateBy());
        con.setContactSummary(clue.getContactSummary());
        con.setAppellation(clue.getAppellation());
        con.setAddress(clue.getAddress());
        int count = contactsDao.save(con);
        if (count!=1){
            System.out.println("添加联系人失败");
            flag=false;
        }
        //我们已将获取了联系人的信息，讲线索备注转换到客户备注和联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        //取出每一条线索备注
        for (ClueRemark clueRemark:clueRemarkList){
            //取出备注信息(主要转换到客户备注和联系人备注的就是这个备注信息)
            String noteContent = clueRemark.getNoteContent();
            //创建客户备注对象,添加客户备注
            //创建联系人备注对象,添加联系人
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setNoteContent(noteContent);
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3!=1){
                flag=false;
            }
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setNoteContent(noteContent);
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4!=1){
                flag=false;
            }
        }
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            String activityId = clueActivityRelation.getActivityId();
            //创建联系人与市场活动的关联关系对象，让第三步生成的联系人与市场活动做关联
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());
            int count4 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count4!=1){
                flag=false;
            }
        }
        if (t!=null){
            System.out.println(t);
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextContactTime(clue.getNextContactTime());
            t.setDescription(clue.getDescription());
            t.setCustomerId(customer.getId());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(con.getId());
            int count5 = tranDao.save(t);
            if (count5!=1){
                flag = false;
            }
            //如果创建了交易，则创建一条该交易的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setTranId(t.getId());
            tranHistory.setStage(t.getStage());
            tranHistory.setMoney(t.getMoney());
            int count6  = tranHistoryDao.save(tranHistory);
            if (count6!=1){
                flag = false;
            }
        }
//        删除线索的备注
//        for (ClueRemark clueRemark:clueRemarkList){
//            int count7 = clueRemarkDao.delete(clueRemark);
//            if (count7!=1){
//                flag  = false;
//            }
//        }
        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            int count8 = clueActivityRelationDao.delete(clueActivityRelation);
            if (count8!=1){
                flag = false;
            }
        }

        int count9 = clueDao.delete(clueId);
        if (count9!=1){
            flag = false;
        }

        return flag;
    }


}
