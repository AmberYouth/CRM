package com.yang.workbench.service;

import com.yang.workbench.entity.Tran;

public interface TranService {
    boolean save(Tran t, String customerName);
}
