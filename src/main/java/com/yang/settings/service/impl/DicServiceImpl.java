package com.yang.settings.service.impl;

import com.yang.settings.dao.DicTypeDao;
import com.yang.settings.dao.DicValueDao;
import com.yang.settings.entity.DicType;
import com.yang.settings.entity.DicValue;
import com.yang.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("dicService")
public class DicServiceImpl implements DicService {
    @Autowired
    @Qualifier("dicTypeDao")
    private DicTypeDao dicTypeDao;
    @Autowired
    @Qualifier("dicValueDao")
    private DicValueDao dicValueDao;
    @Override
    public Map<String, List<DicValue>> getAll() {
//        System.out.println("进入了getAll---------------------------------------");
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> dtList = dicTypeDao.getTypeList();
        for (DicType dt:dtList){
            //取得每一种类型的字典类型编码
            String code = dt.getCode();
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            map.put(code,dvList);
        }
        return map;
    }
}
