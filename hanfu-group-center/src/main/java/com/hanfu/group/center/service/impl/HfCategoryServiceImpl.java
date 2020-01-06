package com.hanfu.group.center.service.impl;

import com.hanfu.group.center.manual.dao.HfCategoryMapper;
import com.hanfu.group.center.manual.model.HfCategory;
import com.hanfu.group.center.manual.model.HfGoods;
import com.hanfu.group.center.service.HfCategoryService;
import com.hanfu.group.center.service.HfGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/25
 * @time: 16:43
 */
@Service
public class HfCategoryServiceImpl implements HfCategoryService {
    @Autowired
    HfCategoryMapper hfCategoryMapper;


    @Override
    public int selectByName(String name) {
        return hfCategoryMapper.selectByName(name);
    }
}
