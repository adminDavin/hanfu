package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.user.center.model.HfUser;

public interface ManualDao {
    public List<UserInfo> getSimpleUserInfo();

    public void selectProductByStone(Integer stoneId);

    public List<Categories> selectCategories();
    
    public HfUser select(Integer userId);
    
    public List<ProductActivityInfo> selectProductActivityList(String activityType);
}
