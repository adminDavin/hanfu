package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.Product;

public interface StoreDao {
	public List<HfStone> selectStoreById(Integer productId);
}
