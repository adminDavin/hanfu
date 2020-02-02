package com.hanfu.product.center.service;




import com.hanfu.product.center.model.HfGoodsSpec;

import java.util.List;


public interface HfGoodsSpecService {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId);
}
