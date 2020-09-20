package com.yang.workbench.service.impl;

import com.yang.workbench.dao.TranDao;
import com.yang.workbench.dao.TranHistoryDao;
import com.yang.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tranServiceImpl")
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

}
