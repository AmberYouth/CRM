package com.yang.workbench.dao;

import com.yang.workbench.entity.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {


    int save(Tran t);

    Tran detail(String id);

    int changeStage(Tran t);

    int getTotal();

    List<Map<String, Object>> getCharts();
}
