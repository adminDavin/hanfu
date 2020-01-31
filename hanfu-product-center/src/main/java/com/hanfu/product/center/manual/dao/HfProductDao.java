package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.HfProductDisplay;

public interface HfProductDao {

    public List<HfProductDisplay> selectProductForRotation(Integer quantity);
    
    public HfProductDisplay selectProduct(Integer productId);
}
