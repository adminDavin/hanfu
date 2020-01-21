package com.hanfu.group.center.service;

import com.hanfu.group.center.manual.model.HfGoods;

import java.util.List;

public interface HfGoodsService {
    List<HfGoods>  selectByPrimaryKey(Integer id);
    List<HfGoods> selectByName(String name);
    Integer selectByPrice(Integer id);
    List<HfGoods> selectAll();
}
