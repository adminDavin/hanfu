package com.hanfu.product.center.service.impl;


import com.hanfu.product.center.dao.HfCategory;
import com.hanfu.product.center.dao.HfGoodsGroupMapper;
import com.hanfu.product.center.service.HfCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class HfCategoryServiceImpl implements HfCategoryService {
    @Autowired
    HfCategory hfCategoryMapper;


    @Override
    public int selectByName(String name) {
        return hfCategoryMapper.selectByName(name);
    }
}
