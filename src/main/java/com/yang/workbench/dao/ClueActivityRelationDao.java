package com.yang.workbench.dao;


import com.yang.workbench.entity.ClueActivityRelation;

public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(ClueActivityRelation car);
}
