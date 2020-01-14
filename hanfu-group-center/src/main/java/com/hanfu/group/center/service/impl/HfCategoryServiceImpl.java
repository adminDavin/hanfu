package com.hanfu.group.center.service.impl;


import com.hanfu.group.center.manual.dao.HfCategoryMapper;
import com.hanfu.group.center.service.HfCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class HfCategoryServiceImpl implements HfCategoryService {
    @Autowired
    HfCategoryMapper hfCategoryMapper;


    @Override
    public int selectByName(String name) {
        return hfCategoryMapper.selectByName(name);
    }
}
