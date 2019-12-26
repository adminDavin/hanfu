package com.hanfu.cart.center.dao;

import com.hanfu.cart.center.model.HfGoods;

public interface HfGoodsDao {
    HfGoods findProductById(String productId);
}
