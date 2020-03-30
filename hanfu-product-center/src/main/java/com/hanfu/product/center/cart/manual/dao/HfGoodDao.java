package com.hanfu.product.center.cart.manual.dao;

import com.hanfu.product.center.cart.manual.model.HfGoods;

public interface HfGoodDao {
    HfGoods findProductById(String productId);
}
