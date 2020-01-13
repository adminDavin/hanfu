package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.model.HfGoods;

import java.util.List;


public interface HfGoodsService {
    List<HfGoods> selectByPrimaryKey(Integer id);
    List<com.hanfu.seckill.center.model.HfGoods> selectAll();
    List<HfGoods>selectByName(String name);
}
