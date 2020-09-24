package com.yang.workbench.dao;

import com.yang.workbench.entity.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getHistoryListByTranId(String id);
}
