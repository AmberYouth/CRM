package com.yang.settings.service;

import com.yang.settings.entity.DicValue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
