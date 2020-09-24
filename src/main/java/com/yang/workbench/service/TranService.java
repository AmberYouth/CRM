package com.yang.workbench.service;

import com.yang.workbench.entity.Tran;
import com.yang.workbench.entity.TranHistory;

import java.util.List;

public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String id);
}
