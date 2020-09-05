package com.yang.settings.dao;

import com.yang.settings.entity.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
