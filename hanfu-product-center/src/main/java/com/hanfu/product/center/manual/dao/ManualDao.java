package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.UserInfo;

public interface ManualDao {
    public List<UserInfo> getSimpleUserInfo();

    public void selectProductByStone(Integer stoneId);

    public List<Categories> selectCategories();
}
