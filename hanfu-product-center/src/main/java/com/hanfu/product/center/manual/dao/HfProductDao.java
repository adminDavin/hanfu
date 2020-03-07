package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.manual.model.IsDelete;
import com.hanfu.product.center.manual.model.ProductNameSelect;

public interface HfProductDao {

    public List<HfProductDisplay> selectProductForRotation(Integer quantity);
    
    public HfProductDisplay selectProduct(Integer productId);
    List<HfProductDisplay> selectProductCategory(Integer cagetoryId);
    List<HfProductDisplay> selectProductByStoneId(IsDelete isDelete);
    List<HfProductDisplay> selectProductByUserId( Integer userId);
    List<HfProductDisplay> selectProductSeniorityId(Integer seniorityId);
    List<HfProductDisplay> selectProductName(ProductNameSelect productNameSelect);
}
