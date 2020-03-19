package com.hanfu.product.center.service;

import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.Product;

import java.util.List;

public interface HfGoodsService {
    List<HfGoods>  selectByPrimaryKey(Integer id);
    List<HfGoods> selectByName(String name);
    Integer selectByPrice(Integer id);
    List<HfGoods> selectAll();
}
