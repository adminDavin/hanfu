package com.hanfu.product.center.service.impl;


import com.hanfu.product.center.dao.HfGoodsGroupMapper;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.service.HfGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/23
 * @time: 9:27
 */
@Service
@Transactional
public class HfGoodsServiceImpl implements HfGoodsService {
    @Autowired
    HfGoodsGroupMapper hfGoodsMapper;
    @Override
    public List<HfGoods>  selectByPrimaryKey(Integer id) {
        return hfGoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<HfGoods> selectByName(String name) {
        return hfGoodsMapper.selectByName(name);
    }

    @Override
    public Integer selectByPrice(Integer id) {
        return hfGoodsMapper.selectByPrice(id);
    }

    @Override
    public List<HfGoods> selectAll() {
        return hfGoodsMapper.selectAll();
    }
}
