package com.hanfu.product.center.manual.dao;

import org.springframework.stereotype.Repository;

import com.hanfu.product.center.model.ProductInstance;

@Repository
public interface ProductInstanceDao {
	
	public Integer deleteProductInstance(ProductInstance example);
	
}
