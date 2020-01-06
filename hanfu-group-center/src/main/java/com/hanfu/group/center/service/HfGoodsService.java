package com.hanfu.group.center.service;

import com.hanfu.group.center.manual.model.HfGoods;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/23
 * @time: 9:26
 */
public interface HfGoodsService {
    List<HfGoods>  selectByPrimaryKey(Integer id);
    List<HfGoods> selectByName(String name);

    List<HfGoods> selectAll();
}
