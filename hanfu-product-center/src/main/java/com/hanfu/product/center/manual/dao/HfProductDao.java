package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.HfProductDisplay;

public interface HfProductDao {

    public List<HfProductDisplay> selectProductForRotation(Integer quantity);
    
    public HfProductDisplay selectProduct(Integer productId);
    List<HfProductDisplay> selectProductCategory(Integer cagetoryId);
    List<HfProductDisplay> selectProductByStoneId(Integer stoneId);
    List<HfProductDisplay> selectProductByUserId( Integer userId);
}
