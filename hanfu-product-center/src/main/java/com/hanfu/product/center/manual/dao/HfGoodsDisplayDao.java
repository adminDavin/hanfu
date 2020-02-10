package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfGoodsSpecDisplay;

public interface HfGoodsDisplayDao {
    public List<HfGoodsDisplayInfo> selectHfGoodsDisplay(Integer productId);
    public HfGoodsDisplayInfo selectHfGoods(Integer goodsId);
    public List<HfGoodsDisplayInfo> selectHfGoodsDisplay(List<Integer> productIds);
    public List<HfGoodsSpecDisplay> selectHfGoodsSpec(Integer goodsId);
    public List<HfGoodsSpecDisplay> selectHfGoodsSpec(List<Integer> goodsIds);
    
}
