package com.hanfu.seckill.center.service.impl;

import com.hanfu.seckill.center.dao.HfGoodsMapper;
import com.hanfu.seckill.center.model.HfGoods;
import com.hanfu.seckill.center.service.HfGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HfGoodsServiceImpl implements HfGoodsService {
    @Autowired
    HfGoodsMapper hfGoodsMapper;
    @Override
    public  List<HfGoods> selectByPrimaryKey(Integer id) {
        return hfGoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<HfGoods> selectAll() {
        return hfGoodsMapper.selectAll();
    }

    @Override
    public List<HfGoods> selectByName(String name) {
        return hfGoodsMapper.selectByName(name);
    }
}
