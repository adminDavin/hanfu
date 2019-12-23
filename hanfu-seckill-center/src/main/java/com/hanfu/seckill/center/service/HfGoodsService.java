package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.model.HfGoods;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/23
 * @time: 9:26
 */
public interface HfGoodsService {
    List<HfGoods> selectByPrimaryKey(Integer id);
    List<com.hanfu.seckill.center.model.HfGoods> selectAll();
    List<HfGoods>selectByName(String name);
}
