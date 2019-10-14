package com.hanfu.product.center.manual.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductInstance;

@Repository
public interface ProductInstanceDao {
	
	public Integer deleteProductInstance(ProductInstance productInstance);
	
}
