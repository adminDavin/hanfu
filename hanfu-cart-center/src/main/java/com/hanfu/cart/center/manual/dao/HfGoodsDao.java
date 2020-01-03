package com.hanfu.cart.center.manual.dao;

import com.hanfu.cart.center.manual.model.HfGoods;

public interface HfGoodsDao {
    HfGoods findProductById(String productId);
}
