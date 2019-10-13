package com.hanfu.product.center.manual.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.Product;

public interface ProductDao {
	public List<Product> selectProductById(Integer storeId);
	
	public List<Product> selectProductBycategoryId(Integer categoryId);
	
	public Integer deleteSelectProduct(Integer[] productId);
	
}
