package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.*;
import com.hanfu.product.center.model.SelectProductGoods;

public interface HfProductDao {

    public List<HfProductDisplay> selectProductForRotation(Integer quantity);
    
    public HfProductDisplay selectProduct(Integer productId,Integer stoneId);
    List<HfProductDisplay> selectProductCategory(Integer cagetoryId);
    List<HfProductDisplay> selectProductByStoneId(IsDelete isDelete);
    List<HfProductDisplay> selectProductByUserId( Integer userId);
    List<HfProductDisplay> selectProductSeniorityId(Integer seniorityId);
    List<HfProductDisplay> selectProductName(ProductNameSelect productNameSelect);
    List<HfProductDisplay> selectActivityProductList(Integer activityId);
    List<ProductGoods> selectProductGoods(SelectProductGoods ProductId);
    List<HomePageInfo> selectProductCount(List<Integer> goodsId);
}
